## CAF_14003 - Invalid Storage Reference sent to BinaryHash Worker ##

Verify that a task sent to BinaryHash worker with an invalid storage reference is placed in the rejected queue after 10 retries.

**Test Steps**

1. Set up system to perform BinaryHash and send a task message to the worker that contains a invalid storage reference
2. Examine the output

**Test Data**

Plain text files

**Expected Result**

The output message is returned with a status of RESULT_FAILURE




