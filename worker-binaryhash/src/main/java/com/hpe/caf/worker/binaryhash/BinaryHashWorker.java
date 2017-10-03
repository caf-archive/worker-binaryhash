/*
 * Copyright 2015-2017 EntIT Software LLC, a Micro Focus company.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hpe.caf.worker.binaryhash;

import com.hpe.caf.api.Codec;
import com.hpe.caf.api.worker.*;
import com.hpe.caf.util.ref.DataSource;
import com.hpe.caf.util.ref.DataSourceException;
import com.hpe.caf.util.ref.ReferencedData;
import com.hpe.caf.worker.AbstractWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Binary Hash worker. This is the class responsible for generating a hash of the input file.
 */
public class BinaryHashWorker extends AbstractWorker<BinaryHashWorkerTask, BinaryHashWorkerResult>
{

    /**
     * Logger for logging purposes.
     */
    private static final Logger LOG = LoggerFactory.getLogger(BinaryHashWorker.class);

    /**
     * Datastore used to store the result/read the reference.
     */
    private final DataStore dataStore;

    public BinaryHashWorker(final BinaryHashWorkerTask task,
                            final DataStore dataStore,
                            final String outputQueue,
                            final Codec codec,
                            final long resultSizeThreshold)
        throws InvalidTaskException
    {
        super(task, outputQueue, codec);
        this.dataStore = Objects.requireNonNull(dataStore);
    }

    @Override
    public String getWorkerIdentifier()
    {
        return BinaryHashWorkerConstants.WORKER_NAME;
    }

    @Override
    public int getWorkerApiVersion()
    {
        return BinaryHashWorkerConstants.WORKER_API_VER;
    }

    /**
     * Trigger processing of the source file and determine a response.
     *
     * @return WorkerResponse - a response from the operation.
     * @throws InterruptedException - if the task is interrupted.
     * @throws TaskRejectedException
     */
    @Override
    public WorkerResponse doWork() throws InterruptedException, TaskRejectedException
    {
        BinaryHashWorkerResult result = processFile();
        if (result.workerStatus == BinaryHashWorkerStatus.COMPLETED) {
            return createSuccessResult(result);
        } else {
            return createFailureResult(result);
        }
    }

    private BinaryHashWorkerResult processFile() throws InterruptedException
    {
        LOG.info("Starting work");
        checkIfInterrupted();

        //Creation of DataSource using dataStore from constructor and serialization codec
        DataSource source = new DataStoreSource(dataStore, getCodec());

        ReferencedData data = getTask().sourceData;

        try {
            //Acquire the inputstream data from the referenced data in the datasource
            InputStream textStream = data.acquire(source);

            //Generate hash 
            String hashResult = DigestUtils.sha1Hex(textStream);

            //create the worker result, outputHash and set worker status complete
            BinaryHashWorkerResult workerResult = new BinaryHashWorkerResult();
            workerResult.hashResult = hashResult;
            workerResult.workerStatus = BinaryHashWorkerStatus.COMPLETED;

            return workerResult;
        } catch (DataSourceException e) {
            //DataSourceException thrown when retrieving data from the datastore
            LOG.warn("Error acquiring data", e);
            return createErrorResult(BinaryHashWorkerStatus.SOURCE_FAILED);
        } catch (IOException e) {
            //IOException thrown if the conversion from InputStream to String fails
            LOG.warn("Error converting input stream to text", e);
            return createErrorResult(BinaryHashWorkerStatus.WORKER_FAILED);
        }
    }

    /**
     * If an error in the worker occurs, create a new BinaryHashWorkerResult with the corresponding worker failure status.
     */
    private BinaryHashWorkerResult createErrorResult(BinaryHashWorkerStatus status)
    {
        BinaryHashWorkerResult workerResult = new BinaryHashWorkerResult();
        workerResult.workerStatus = status;
        return workerResult;
    }
}
