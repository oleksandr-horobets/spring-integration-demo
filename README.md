# Spring Integration Demo project

This is an educational project I started to get familiar with Spring Integration features.
I've tried to cover all important features of this amazing framework and to apply as much Enterprise Integration
Patterns as I can. The project contains a lot of examples of using Spring Integration features. You are welcome to use
them.

## Project overview

Consider a typical e-commerce system. It's able to receive orders via following methods:

- Web Interface
- Mobile application
- E-mail

There are two major categories in e-store: mobile phones and laptops.
Mobile phones are supplied by a Partner ABC and laptops are supplied by a Partner XYZ.

## Receiving a new order
There are two existing applications for receiving orders: a web application using SQL database and a mobile application
using REST web-service in order to create new orders. We need to integrate those two applications with other enterprise
systems.

Unfortunately web app is legacy and we are not able to change its code. Luckily we have access to its database.
So we will use JDBC inbound channel adapter to integrate it with other systems.

Mobile application is already developed but still not in production. There are specs for REST service, but there is no
service itself. So we will use Spring Integration HTTP inbound channel adapter.

Also our senior manager would be happy to offer our customers to send new orders just via email message. We will
implement this feature using Mail-Receiving channel adapter.