# Configuring filestore for integration tests
On the host machine running integration tests, define the CAF\_INTEGRATIONTESTS\_FS\_LOCATION environment variable with the path of the directory where the filesystem datastore will be created.

If the integration tests are to be executed on a machine that is running Docker containers in a vagrant-mesos VM then CAF\_INTEGRATIONTESTS\_FS\_LOCATION ***must*** be defined as the path of the vagrant-mesos directory, e.g. on a Windows development PC this would be `C:\some\path\vagrant-mesos`.

If the integration tests are to be executed on a machine that is running Docker containers directly (e.g. the build machine, caf-builder) then CAF\_INTEGRATIONTESTS\_FS\_LOCATION must be defined as "/vagrant".

| Integration Test Host | CAF\_INTEGRATIONTESTS\_FS\_LOCATION | Actual path to filestore on integration test host | Docker host path mapped to Docker container volume | Filstore path within Docker container |
|:-------------|:------------- |:------------- |:-------------| -----|
|Windows host running Linux VM running Docker container| C:\some\path\vagrant-mesos | C:\some\path\vagrant-mesos\fsdatastore | /vagrant/fsdatastore | /vagrant/fsdatastore |
|Linux host running Linux VM running Docker container [e.g. Trev]| /some/path/vagrant-mesos | /some/path/vagrant-mesos/fsdatastore | /vagrant/fsdatastore | /vagrant/fsdatastore |
|Linux host running Docker container [e.g. caf-builder]| /vagrant |/vagrant/fsdatastore |  /vagrant/fsdatastore | /vagrant/fsdatastore |