# Binary Hash Worker

The Binary Hash Worker can process files which:

- are passed directly to it (i.e. as part of the input message)
- are stored centrally and where only the storage reference is passed to it.

It generates a SHA-1 digest for the specified file and returns it as a 40 character hex-encoded string.

## Modules

### worker-binaryhash-shared
This is the shared library defining public classes that constitute the worker interface to be used by consumers of the Binary Hash Worker. The project can be found in [worker-binaryhash-shared](worker-binaryhash-shared).

### worker-binaryhash
This project contains the actual implementation of the Binary Hash Worker. It can be found in [worker-binaryhash](worker-binaryhash).

### worker-binaryhash-container  
This project builds a Docker image that packages the Binary Hash Worker for deployment. It can be found in [worker-binaryhash-container](worker-binaryhash-container-fs).

### worker-binaryhash-testing
This contains implementations of the testing framework to allow for integration testing of the Binary Hash Worker. The project can be found in [worker-binaryhash-testing](worker-binaryhash-testing).

## Feature Testing
The testing for the Binary Hash Worker is defined [here](testcases)
