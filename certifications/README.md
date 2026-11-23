# Certification Roadmap

> **Status:** Learning plan — certifications listed here have **not yet been earned**.
>
> **Career goal:** Build practical skills for Cloud Infrastructure and Observability Engineering roles.

## Planned certifications

| Certification | Main topics | Status | Lab evidence to build |
|---|---|---|---|
| Microsoft Azure Fundamentals (AZ-900) | Cloud concepts, Azure services, governance and pricing | Planned | Document Azure fundamentals and compare cloud services with the local lab |
| Kubernetes and Cloud Native Associate (KCNA) | Kubernetes basics, cloud-native architecture, observability | Planned | Deploy Nginx on Minikube, manage Services and ConfigMaps, troubleshoot pods |
| Prometheus Certified Associate (PCA) | Prometheus architecture, PromQL, metrics, alerting | Planned | Query Prometheus metrics, monitor Nginx, build recording/alerting rules |
| ITIL Foundation (Version 5) | IT service management, change enablement, incident and problem management, continual improvement | Planned | Document an incident investigation, change review and monitoring service improvement scenario |
| Certified Kubernetes Application Developer (CKAD) | Kubernetes application design, configuration, deployment and troubleshooting | Future goal | Build and troubleshoot multi-container application workloads |

## Suggested learning order

1. **KCNA** — consolidate the Kubernetes fundamentals already practised in Minikube.
2. **PCA** — deepen Prometheus and PromQL knowledge using the running monitoring stack.
3. **AZ-900** — establish Azure cloud fundamentals for Cloud Infrastructure roles.
4. **ITIL Foundation (Version 5)** — connect operational experience with service management practices.
5. **CKAD** — consider after more Kubernetes hands-on practice.

This order is a suggested study sequence, not a booking schedule. Exam availability, syllabuses and fees should be checked on the official certification websites before registering.

## Current hands-on learning evidence

- [x] Created a GitHub repository for the learning lab.
- [x] Used Git branches, commits, pushes and pull requests.
- [x] Merged three pull requests for Kubernetes manifests and a UTF-8 fix.
- [x] Deployed an Nginx website to Minikube.
- [x] Used Kubernetes Deployment, Service and ConfigMap manifests.
- [x] Practised `kubectl get`, `apply`, `exec`, `port-forward` and troubleshooting.
- [x] Started a Docker-based Prometheus, Grafana and cAdvisor lab.
- [ ] Monitor Nginx metrics in Prometheus.
- [ ] Build a Grafana dashboard for Nginx.
- [ ] Configure and test a Prometheus alert.
- [ ] Complete practice exams and book certification exams.

## Progress tracker

| Certification | Study started | Labs completed | Practice exam | Exam booked | Passed |
|---|---|---|---|---|---|
| AZ-900 | [ ] | [ ] | [ ] | [ ] | [ ] |
| KCNA | [ ] | [ ] | [ ] | [ ] | [ ] |
| PCA | [ ] | [ ] | [ ] | [ ] | [ ] |
| CKAD | [ ] | [ ] | [ ] | [ ] | [ ] |

## Study notes

Create a subfolder for each certification as you learn:

```text
certifications/
├── README.md
├── az-900/
├── kcna/
├── pca/
└── ckad/
```

For each topic, record:

- **Concept:** What it means in your own words.
- **Command or query:** What you ran in the lab.
- **Expected result:** What success looks like.
- **Troubleshooting:** What failed and how you fixed it.
- **Evidence:** A YAML file, dashboard export, screenshot or terminal output (without secrets).

## CV guidance

Until a certification is passed, list it as **In progress** only if you have actually begun preparing for it, or **Planned** otherwise. Do not place planned certifications under a section titled *Certifications Earned*.
