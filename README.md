[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/IMEm063v)
# Micro WebPoS 


请参考spring-petclinic-rest/spring-petclinic-microserivces 将webpos项目改为微服务架构，具体要求包括：
1. 至少包含独立的产品管理服务、订单管理服务以及discovery/gateway等微服务架构下需要的基础设施服务；
2. 请将系统内的不同微服务实现不同的计算复杂度，通过压力测试实验验证对单个微服务进行水平扩展（而无需整个系统所有服务都进行水平扩展）可以提升系统性能，请给出实验报告；
3. 请使用`RestTemplate`进行服务间访问，验证Client-side LB可行；
4. 请注意使用断路器等机制；
5. 如有兴趣可在kubernetes或者minikube上进行部署。

请编写readme对自己的系统和实验进行详细介绍。


# 系统结构

我的系统的微服务分为5个部分：webpos-carts，webpos-products，webpos-counter，webpos-orders，webpos-discovery，webpos-gateway。其中webpos-carts通过调用webpos-counter来计算购物车的总价。webpos-products提供了商品的信息。webpos-orders提供了订单的信息。webpos-discovery是服务发现的服务。webpos-gateway是网关服务。

webpos-gateway --> webpos-carts --> webpos-counter
               --> webpos-products

在webpos-carts调用webpos-counter服务时，会通过RestTemplate进行服务间访问，验证Client-side LB可行。同时，我在webpos-counter中使用了断路器机制。

# 实验报告

为了更好的体现单个服务水平扩展，我在webpos-counter中添加了一个循环来提高计算复杂度，大幅提高了webpos-counter的相应时间。

测试方面，我使用gatling进行测试。针对响应时间长的请求，通过/carts/checkout/{cartId}来请求webpos-carts让其通过负载均衡调用webpos-counter。我在webpos-counter中添加了一个循环来提高计算复杂度，大幅提高了webpos-counter的相应时间。最终我的压力测试使用并行的20个user发出上述请求，每个user重复10次。分为两组实验，A组的每个微服务都有一个实例，限制webpos-carts, webpos-products, webpos-counter的cpus为2.另一组B设置webpos-counter的cpus为2，scale数量为3，其余微服务设置不变进行测试。

|      | Mean  | 50th pct | 75TH PCT | 95th PCT |
| ---- | ----- | -------- | -------- | -------- |
| A组  | 16378 | 16893    | 17208    | 18099    |
| B组  | 2993  | 2461     | 4417     | 5034     |

所以仅通过对微服务应用的瓶颈应用进行水平扩展即可以提供性能。
