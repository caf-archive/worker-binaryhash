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

import com.hpe.caf.worker.testing.*;
import com.hpe.caf.worker.testing.execution.AbstractTestControllerProvider;

public class BinaryHashTestControllerProvider extends AbstractTestControllerProvider<
    BinaryHashWorkerConfiguration, BinaryHashWorkerTask, BinaryHashWorkerResult, BinaryHashTestInput, BinaryHashTestExpectation>
{
    public BinaryHashTestControllerProvider()
    {
        super(BinaryHashWorkerConstants.WORKER_NAME,
              BinaryHashWorkerConfiguration::getOutputQueue,
              BinaryHashWorkerConfiguration.class,
              BinaryHashWorkerTask.class,
              BinaryHashWorkerResult.class,
              BinaryHashTestInput.class,
              BinaryHashTestExpectation.class);
    }

    @Override
    protected WorkerTaskFactory<BinaryHashWorkerTask, BinaryHashTestInput, BinaryHashTestExpectation>
        getTaskFactory(TestConfiguration<BinaryHashWorkerTask, BinaryHashWorkerResult, BinaryHashTestInput, BinaryHashTestExpectation> configuration)
        throws Exception
    {
        return new BinaryHashTaskFactory(configuration);
    }

    @Override
    protected ResultProcessor getTestResultProcessor(
        TestConfiguration<BinaryHashWorkerTask, BinaryHashWorkerResult, BinaryHashTestInput, BinaryHashTestExpectation> configuration,
        WorkerServices workerServices)
    {
        return new BinaryHashResultValidationProcessor(configuration, workerServices);
    }

    @Override
    protected TestItemProvider getDataPreparationItemProvider(TestConfiguration<BinaryHashWorkerTask, BinaryHashWorkerResult, BinaryHashTestInput, BinaryHashTestExpectation> configuration)
    {
        return new BinaryHashResultPreparationProvider(configuration);
    }

    @Override
    protected ResultProcessor getDataPreparationResultProcessor(
        TestConfiguration<BinaryHashWorkerTask, BinaryHashWorkerResult, BinaryHashTestInput, BinaryHashTestExpectation> configuration,
        WorkerServices workerServices)
    {
        return new BinaryHashSaveResultProcessor(configuration, workerServices);
    }
}
