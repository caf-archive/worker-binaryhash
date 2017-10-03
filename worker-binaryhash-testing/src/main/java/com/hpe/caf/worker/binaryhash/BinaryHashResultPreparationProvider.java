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

import com.hpe.caf.worker.testing.TestConfiguration;
import com.hpe.caf.worker.testing.TestItem;
import com.hpe.caf.worker.testing.preparation.PreparationItemProvider;

import java.nio.file.Path;

/**
 * Result Preparation Provider for the Markup Worker to generate test items in test case generation mode.
 */
public class BinaryHashResultPreparationProvider extends
    PreparationItemProvider<BinaryHashWorkerTask, BinaryHashWorkerResult, BinaryHashTestInput, BinaryHashTestExpectation>
{
    TestConfiguration configuration;

    public BinaryHashResultPreparationProvider(TestConfiguration<BinaryHashWorkerTask, BinaryHashWorkerResult, BinaryHashTestInput, BinaryHashTestExpectation> configuration)
    {
        super(configuration);
        this.configuration = configuration;
    }

    @Override
    protected TestItem createTestItem(Path inputFile, Path expectedFile) throws Exception
    {
        TestItem<BinaryHashTestInput, BinaryHashTestExpectation> item = super.createTestItem(inputFile, expectedFile);
        BinaryHashWorkerTask task = getTaskTemplate();

        if (task == null) {

        } else {
            item = updateItem(item, task, inputFile);
        }

        return item;
    }

    private TestItem updateItem(TestItem<BinaryHashTestInput, BinaryHashTestExpectation> item, BinaryHashWorkerTask task, Path inputFile)
    {
        item.getInputData().setUseDataStore(false);
        item.getInputData().setInputFile(inputFile.toString());

        return item;
    }
}
