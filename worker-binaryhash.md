# Binary Hash Worker API

## Version Matrix and Dependencies

The following components are dependencies of `worker-binaryhash` version 2.0.0:

- [worker-caf](https://github.com/WorkerFramework/worker-framework/tree/develop/worker-caf) - version 1.6.0
- [worker-binaryhash-shared](worker-binaryhash-shared) - version 2.0.0
- [caf-api](https://github.com/CAFapi/caf-common/tree/develop/caf-api) - version 1.8.0
- [util-moduleloader](https://github.com/CAFapi/caf-common/tree/develop/util-moduleloader) - version 1.8.0

The worker's image is built by [worker-binaryhash-container](worker-binaryhash-container)

## General Operation Overview

The Worker will take a single input message, expecting to find a reference to data accessed either directly or via a DataStore. From this it will then produce a SHA-1 hash (40 character hex string).

## Configuration

The Binary Hash Worker uses the standard `CAF-API` system of `ConfigurationSource`. The worker specific configuration is [BinaryHashWorkerConfiguration](worker-binaryhash/src/main/java/com/hpe/caf/worker/binaryhash/BinaryHashWorkerConfiguration.java) which has the options:

- `outputQueue`: the output queue to return results to RabbitMQ.
- `threads`: the number of threads to use in the worker.
- (`resultSizeThreshold`: not currently used.)

### Environment Variable Configuration

The worker container supports reading its configuration solely from environment variables. To enable this mode do not pass the `CAF_APPNAME` and `CAF_CONFIG_PATH` environment variables to the worker. This will cause it to use the default configuration files embedded in the container which check for environment variables. A listing of the RabbitMQ and Storage properties is available [here](https://github.com/WorkerFramework/worker-framework/tree/develop/worker-default-configs).

The Binary Hash Worker specific configuration is described below;

| Property | Checked Environment Variables | Default               |
|----------|-------------------------------|-----------------------|
| outputQueue  |  `CAF_WORKER_OUTPUT_QUEUE`                                                      | worker-out  |
|              |   `CAF_WORKER_BASE_QUEUE_NAME` with '-out' appended to the value if present     |             |
|              |  `CAF_WORKER_NAME` with '-out' appended to the value if present                 |             |
| threads      |   `CAF_WORKER_THREADS`                                                |             1         |
| resultSizeThreshold   |  `CAF_BINARYHASH_WORKER_RESULT_SIZE_THRESHOLD`                         |   1024      |

## Input Task Format

There are additional configurations to be supplied by the user on a per-task basis. These are passed to the Worker in the JSON message. A description of the worker's task message is shown below, along with the outputHash. 


#### MarkupWorkerTask

|    Component          |     Description    |
| --------------------- | ------------------ |
| `sourceData` | (required): a reference to the data accessible either directly or via the DataStore 

## Output Message

The result class is [BinaryHashWorkerResult](worker-binaryhash-shared/src/main/java/com/hpe/caf/worker/binaryhash/BinaryHashWorkerResult.java) and is shown below.

|    Component          |     Description    |
| --------------------- | ------------------ |
| `workerStatus` | The worker specific return code depicting the processing result status. Any other value that `COMPLETED` means failure. The possible worker statuses are: <br/> - `COMPLETED`: the worker processed the task successfully. <br/> - `WORKER_FAILED`: the worker failed in an unexpected way. <br/> - `STORE_FAILED`: The worker failed to store or retrieve from datastore. (not currently in use) |
| `hashResult` | A SHA-1 hash (40 character hexidecimal string) of the input file. |

## Health Checks

This worker provides a basic health check by returning `HEALTHY` if it can communicate with Marathon.

## Resource Usage

The number of threads is configured using the `threads` setting in the [Binary Hash Worker Configuration](#configuration).

Memory usage will remain constant regardless of the size of the file being processed.

## Failure Modes

There are three main places where this worker can fail:

- Configuration errors: these will manifest on startup and cause the worker not to start at all. Check the logs for clues, and double check your configuration files.
- `WORKER_FAILED`: Tasks coming from the worker with this worker status have failed during processing in some unexpected way.
- `SOURCE_FAILED`: The source data could not be acquired from the data storage. 
- `STORE_FAILED`: The worker has failed to store data in data storage.

## Upgrade Procedures

These follow standard CAF Worker upgrade procedures. If the version of `worker-binaryhash-shared` has not changed then an upgrade to `worker-binaryhash` is an in-place upgrade.

If you need to do a rolling upgrade when `worker-binaryhash-shared` has not changed then:
- Spin up containers of the new version of `worker-binaryhash`.
- Replace old versions of producers of `BinaryHashWorkerTask` with new ones.
- Allow the queue with the old versions of `BinaryHashWorkerTask` to drain then shut down the old worker containers.
