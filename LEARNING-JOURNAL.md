# Cloud, Kubernetes & Observability Learning Journal

**Repository:** [observerbility-learning-lab](https://github.com/boakgomo/observerbility-learning-lab)  
**Last updated:** 23 November 2026  
**Goal:** Build practical skills for Cloud Infrastructure and Observability Engineering while documenting hands-on work.

> This journal records what I have practised so far. Topics marked **Next to learn** are not claimed as completed experience.

## 1. Lab environment

- MacBook Air with Apple M1 chip and 8 GB RAM.
- Docker Desktop for running containers.
- Minikube using the Docker driver for a local Kubernetes cluster.
- `kubectl` for interacting with Kubernetes.
- Git and GitHub for version control.
- Prometheus, Grafana and cAdvisor containers for introductory monitoring exercises.

### Environment checks

```bash
docker version
docker ps
minikube status
kubectl get nodes
kubectl get pods -A
```

**Lesson:** When `kubectl` cannot reach the API server, check whether Docker Desktop and Minikube are running before changing application manifests. I recovered a stopped Minikube environment using:

```bash
minikube start --driver=docker
```

## 2. Docker fundamentals

- Installed and tested Docker Desktop; successfully ran the Docker hello-world example.
- Used Docker containers for an observability practice stack.
- Learned to inspect running containers with `docker ps`.
- Practised using locally exposed ports for Prometheus, Grafana and cAdvisor.

**Concept:** A container packages an application and its dependencies. Kubernetes can orchestrate containers as Pods.

## 3. Kubernetes fundamentals and hands-on practice

### Cluster and workloads

- Started a local Minikube cluster and verified its node was Ready.
- Deployed an Nginx-based website (`my-website`).
- Practised scaling a Deployment between one and three replicas and back to one.
- Inspected Deployments, Pods, ReplicaSets, Services and endpoints.
- Observed a separate `web-server` Deployment with two ready Pods.

```bash
kubectl get nodes
kubectl get deployments
kubectl get pods
kubectl get replicasets
kubectl get services
kubectl get endpoints my-website
kubectl scale deployment my-website --replicas=3
kubectl scale deployment my-website --replicas=1
```

### Deployment

The tracked file `kubernetes/my-website-deployment.yaml` defines:

- Nginx image: `nginx:alpine`.
- Desired replica count: one.
- RollingUpdate deployment strategy.
- Liveness and readiness HTTP probes on port 80.
- CPU request/limit: `25m` / `200m`.
- Memory request/limit: `32Mi` / `128Mi`.
- A read-only volume mount for website content from the `website-html` ConfigMap.

**Lesson:** A Deployment describes the desired application state and manages Pods through ReplicaSets. Readiness and liveness probes serve different purposes: traffic readiness versus container health.

### Service

The file `kubernetes/my-website-service.yaml` defines a `ClusterIP` Service that selects Pods labelled `app: my-website` and forwards port 80 to port 80.

**Lesson:** A Service provides a stable network endpoint for matching Pods. The cluster-assigned IP should generally not be hardcoded in reusable YAML.

### ConfigMap

The file `kubernetes/website-configmap.yaml` stores `index.html` for the website. The Deployment mounts it at `/usr/share/nginx/html`.

**Lesson:** A ConfigMap can separate non-secret application configuration or content from the container image. It should not be used to store passwords or tokens.

### Accessing the website

```bash
kubectl port-forward service/my-website 8085:80
```

Then open `http://localhost:8085` in a browser. Port forwarding works only while the command remains running.

## 4. Exporting and maintaining Kubernetes YAML

I exported running resources to YAML, then cleaned Kubernetes-generated metadata before committing them:

```bash
kubectl get deployment my-website -o yaml > kubernetes/my-website-deployment.yaml
kubectl get service my-website -o yaml > kubernetes/my-website-service.yaml
kubectl get configmap website-html -o yaml > kubernetes/website-configmap.yaml
```

**Fields commonly removed from reusable exports:** `uid`, `resourceVersion`, `creationTimestamp`, `status`, and cluster-assigned Service IPs. Keep the application-specific `spec`, labels, selectors, probes, resource limits and ConfigMap references.

### Validation and change preview

```bash
kubectl apply --dry-run=client -f kubernetes/
kubectl diff -f kubernetes/
```

- `--dry-run=client` checks manifests without persisting changes.
- `kubectl diff` previews proposed differences between local manifests and the live cluster.
- A `last-applied-configuration` warning can appear for existing resources that were not originally created using `kubectl apply`.

In my lab, the diff showed an added `containerPort: 80` declaration and HTML formatting differences. I learned that a change to a Deployment Pod template may trigger a rolling update.

## 5. Git and GitHub workflow

### Repository

[github.com/boakgomo/observerbility-learning-lab](https://github.com/boakgomo/observerbility-learning-lab)

### Commands practised

```bash
git status
git branch -avv
git switch main
git pull --ff-only origin main
git switch -c feature/example-change
git diff
git add <file>
git commit -m "Describe the change"
git push -u origin feature/example-change
git fetch origin
git log --oneline --graph --decorate --all -10
```

### Completed pull requests

1. [PR #1 — Add Kubernetes Nginx deployment manifest](https://github.com/boakgomo/observerbility-learning-lab/pull/1) — merged.
2. [PR #2 — Add Kubernetes Service and ConfigMap manifests](https://github.com/boakgomo/observerbility-learning-lab/pull/2) — merged.
3. [PR #3 — Fix UTF-8 encoding in Kubernetes website](https://github.com/boakgomo/observerbility-learning-lab/pull/3) — merged.

### Key lessons

- `main` is the primary branch; feature branches isolate changes.
- A commit saves a local snapshot; `git push` publishes it to GitHub.
- Pull requests enable review before merging.
- After a merge, `git pull --ff-only origin main` updates the local `main` branch.
- GitHub may say **“There isn't anything to compare”** when a branch has already been merged.
- `nothing to commit, working tree clean` means no uncommitted changes remain.
- A message saying a branch already exists can result from running `git switch -c` twice.
- Merging a pull request does **not** automatically deploy changes to Minikube.

## 6. Troubleshooting case study: broken emoji

**Symptom:** The website displayed `ðŸ”¥` instead of the fire emoji (🔥).

**Investigation and fix:**

1. Confirmed the website was accessible through the Service and `kubectl port-forward`.
2. Added `<meta charset="UTF-8">` inside the HTML `<head>` in `website-configmap.yaml`.
3. Validated the updated ConfigMap using `kubectl apply --dry-run=client`.
4. Applied the ConfigMap to Minikube and verified the updated file inside the Pod:

   ```bash
   kubectl apply -f kubernetes/website-configmap.yaml
   kubectl exec deployment/my-website -- grep charset /usr/share/nginx/html/index.html
   ```

5. Refreshed the browser and confirmed the issue was fixed.
6. Committed the change on `feature/fix-website-encoding` and merged PR #3.

**Lesson:** Separate a local file change, a GitHub merge, a live Kubernetes configuration update and browser rendering. They are distinct steps.

## 7. Introductory observability lab

I have already set up and explored:

- **Prometheus:** metrics collection and scrape targets.
- **Grafana:** dashboard creation and visualization practice.
- **cAdvisor:** container resource metrics.
- **Kubernetes metrics:** `kubectl top nodes` and `kubectl top pods`.

Earlier lab checks showed Prometheus scraping itself and cAdvisor, with targets reporting UP. This is introductory hands-on practice, not yet a production observability deployment.

## 8. Next to learn

- Monitor the Nginx application with suitable application and container metrics.
- Build and version-control a Grafana dashboard.
- Learn PromQL and Prometheus alerting rules.
- Practise Kubernetes rollouts and rollbacks safely.
- Introduce GitHub Actions for validation; later explore Argo CD for actual GitOps reconciliation.
- Document each exercise with commands, observed output, problems and resolutions.

## 9. Useful daily commands

```bash
# Git
git status
git log --oneline -5

# Docker
docker ps

# Kubernetes
minikube status
kubectl get nodes
kubectl get deployments,services,configmaps
kubectl get pods
kubectl describe pod <pod-name>
kubectl logs <pod-name>

# Preview manifests
kubectl apply --dry-run=client -f kubernetes/
kubectl diff -f kubernetes/
```

---

**Portfolio principle:** Document what I actually built, tested and troubleshot. Distinguish hands-on lab experience from technologies I am still learning.
