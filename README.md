# Steps
- Build docker images at the root folder
  - `docker build -t front-service -f ./front-service/Dockerfile .`
  - `docker build -t middle-service -f ./middle-service/Dockerfile .`
  - `docker build -t back-service -f ./back-service/Dockerfile .`
- Tag images
  - `docker tag front-service asia.gcr.io/<project id>/front-service:0.3.0`
  - `docker tag middle-service asia.gcr.io/<project id>/middle-service:0.1.0`
  - `docker tag back-service asia.gcr.io/<project id>/back-service:0.1.0`
- Push images to gcp container registry
  - `docker push asia.gcr.io/<project id>/front-service:0.3.0`
  - `docker push asia.gcr.io/<project id>/middle-service:0.1.0`
  - `docker push asia.gcr.io/<project id>/back-service:0.1.0`
- Deploy applications
  - `kubectl apply -f kubernetes-front-service-gcp.yaml`
  - `kubectl apply -f kubernetes-middle-service-gcp.yaml`
  - `kubectl apply -f kubernetes-back-service-gcp.yaml`
- Get external ip of `front-service` application from `kubectl get svc`
  ```shell
  # Query kubernetes service info command
  kubectl get svc
  # Output sample
  NAME                 TYPE           CLUSTER-IP      EXTERNAL-IP     PORT(S)        AGE
  back-service-svc     LoadBalancer   10.102.131.1    34.80.143.76    80:32615/TCP   144m
  front-service-svc    LoadBalancer   10.102.128.11   34.80.176.214   80:32439/TCP   42m
  kubernetes           ClusterIP      10.102.128.1    <none>          443/TCP        3d4h
  middle-service-svc   LoadBalancer   10.102.130.39   34.81.86.5      80:31668/TCP   145m
  ```
- Access api via `https://<external ip of front service>/front-entry`
  - in this example: `https://34.80.176.214/front-entry`
- Go to GCP Operations Logging to see the logs ![](/image/cloud_operations_logging.png)
---
# Others
- If you want to compile the project in the local machine, you need to obtain default GCP Application Default Credentials via `gcloud auth application-default login`
  - ref.: https://cloud.google.com/sdk/gcloud/reference/auth/application-default/login