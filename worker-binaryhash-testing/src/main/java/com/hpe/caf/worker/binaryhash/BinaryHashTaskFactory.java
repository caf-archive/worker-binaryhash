/*
 * Copyright 2017-2018 Micro Focus or one of its affiliates.
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

import com.hpe.caf.util.ref.ReferencedData;
import com.hpe.caf.worker.testing.FileInputWorkerTaskFactory;
import com.hpe.caf.worker.testing.TestConfiguration;
import com.hpe.caf.worker.testing.TestItem;

public class BinaryHashTaskFactory extends FileInputWorkerTaskFactory<BinaryHashWorkerTask, BinaryHashTestInput, BinaryHashTestExpectation>
{
    public BinaryHashTaskFactory(TestConfiguration configuration) throws Exception
    {
        super(configuration);
    }

    @Override
    public String getWorkerName()
    {
        return BinaryHashWorkerConstants.WORKER_NAME;
    }

    @Override
    public int getApiVersion()
    {
        return BinaryHashWorkerConstants.WORKER_API_VER;
    }

    @Override
    protected BinaryHashWorkerTask createTask(TestItem<BinaryHashTestInput, BinaryHashTestExpectation> testItem,
                                              ReferencedData sourceData)
    {
        BinaryHashWorkerTask task = new BinaryHashWorkerTask();

        task.sourceData = sourceData;
        return task;
    }
}
