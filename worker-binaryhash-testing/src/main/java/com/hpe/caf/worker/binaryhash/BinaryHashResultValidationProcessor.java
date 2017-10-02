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

import com.hpe.caf.api.worker.TaskMessage;
import com.hpe.caf.worker.testing.*;
import com.hpe.caf.worker.testing.configuration.ValidationSettings;
import com.hpe.caf.worker.testing.validation.PropertyValidatingProcessor;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BinaryHashResultValidationProcessor extends PropertyValidatingProcessor<BinaryHashWorkerResult, BinaryHashTestInput, BinaryHashTestExpectation>
{
    public BinaryHashResultValidationProcessor(
        TestConfiguration<BinaryHashWorkerTask, BinaryHashWorkerResult, BinaryHashTestInput, BinaryHashTestExpectation> configuration,
        WorkerServices workerServices)
    {

        super(configuration, workerServices, ValidationSettings.configure().build());
    }

    @Override
    protected boolean processWorkerResult(TestItem<BinaryHashTestInput, BinaryHashTestExpectation> testItem,
                                          TaskMessage message,
                                          BinaryHashWorkerResult workerResult)
        throws Exception
    {

        assertEquals(testItem.getExpectedOutputData().status, workerResult.workerStatus);
        return super.processWorkerResult(testItem, message, workerResult);
    }

    @Override
    protected boolean isCompleted(TestItem<BinaryHashTestInput, BinaryHashTestExpectation> testItem,
                                  TaskMessage message, BinaryHashWorkerResult result)
    {
        return true;
    }

    @Override
    protected Map<String, Object> getExpectationMap(TestItem<BinaryHashTestInput, BinaryHashTestExpectation> testItem,
                                                    TaskMessage message, BinaryHashWorkerResult result)
    {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("workerStatus", testItem.getExpectedOutputData().status);
        map.put("hashResult", testItem.getExpectedOutputData().hashResult);
        return map;
    }

    @Override
    protected Object getValidatedObject(TestItem<BinaryHashTestInput, BinaryHashTestExpectation> testItem,
                                        TaskMessage message, BinaryHashWorkerResult result)
    {
        return result;
    }
}
