# Dummy Betting Application

The idea behind this project was to create a dummy betting application that simulates the process of creating a ticket by selecting 
possible outcomes of upcoming sport events.

The application itself can be broken down into 4 components (microservices):
* Database component which is used to store data -> MongoDB
* Provisioning component which is used to provision the database with the necessary information so it can later be accessed -> Java/SpringBoot
* Data access component which offers an easy way to access the data -> Java/SpringBoot
* Front end component which defines the user interface -> VueJs

 All components are packaged as Docker images and embedded into relevant Kubernetes objects. Kubernetes objects necessary for running the application 
 are placed in a single Helm chart and MongoDB bitnami helm chart is listed as a dependency of that chart. This implies that you need to have Docker, Kubernetes(minikube) and Helm installed if you want to be able to 
 run the application.

## Build and Deploy

Execute the command below from the root of the repository to build all images:

```
  # ./build.sh -b all
```

Execute the command below to deploy the chart:

```
  # helm install --name=dummy-betting-app  helm/test-app/
```

List pods until you see an output similar to the one below:

```
  # kubectl get po
  NAME                           READY   STATUS      RESTARTS   AGE
  data-access-7cdd84445f-svdv5   1/1     Running     0          78s
  data-mongodb-0                 1/1     Running     0          78s
  data-mongodb-1                 1/1     Running     0          65s
  data-mongodb-arbiter-0         1/1     Running     0          78s
  front-end-97dfd6f4b-lm2qk      1/1     Running     0          78s
  provisioning-app-job-62xxq     0/1     Completed   0          78s
```

Run your browser and go to **http://[kubernetes worker IP]:30033** to use the application!