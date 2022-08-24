# 1. prerequisite: cert-manager 설치
```console
helm repo add jetstack https://charts.jetstack.io
helm repo update
helm install cert-manager jetstack/cert-manager --namespace cert-manager --create-namespace --set installCRDs=true
```

# 2. k8ssandra-operator 설치
```console
helm repo add k8ssandra https://helm.k8ssandra.io/stable
helm repo update
helm install k8ssandra-operator k8ssandra/k8ssandra-operator -n k8ssandra-operator --create-namespace
```

# 3. k8ssandra-operator 적용
```console
kubectl -n k8ssandra-operator apply -f k8ssandra.yaml
```

# 4. k8ssandra loadbalancer 적용
```console
kubectl -n k8ssandra-operator apply -f k8ssandra-loadbalancer.yaml
```

# 5. k8ssandra 동작 확인
```console
kubectl get CassandraDatacenter -n k8ssandra-operator
kubectl get pods -n k8ssandra-operator
kubectl get k8cs -n k8ssandra-operator
kubectl get svc -n k8ssandra-operator

CASS_USERNAME=$(kubectl get secret featurestore-superuser -n k8ssandra-operator -o=jsonpath='{.data.username}' | base64 --decode)
CASS_PASSWORD=$(kubectl get secret featurestore-superuser -n k8ssandra-operator -o=jsonpath='{.data.password}' | base64 --decode)
kubectl exec -it featurestore-dc1-default-sts-0 -n k8ssandra-operator -c cassandra -- nodetool -u $CASS_USERNAME -pw $CASS_PASSWORD status
```
