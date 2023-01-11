# Github-Trending-Repositories

As an architecture in this project I've used MVVM and Clean architectures, and I took into account SOLID principles. These  architectures have a lot of advantages when adding tests to a project. They were my choice of architecture because layers are decoupled and each layer allows us to have  its logic, without being tightly coupled with other layers. We have presentation layer that is basically Fragments, were we don't have any business logic. In the domain layer we have use cases that can delegate their jobs to repositories, and repositories in their turn have access to local and remote data.

The technical stack is: Java, RxJava for threading, Retrofit for API calls, Hilt for DI, Jetpack Pagination, MVVM, Clean Architecture, SOLID, Room, Android Jetpack Navigation.

For the asynchronous programming we have  RxJava. For now it is the best clean and powerful library for working with Java threads.

I have implemented all additional and required features.

If I had more time, I would definitely add unit tests and UI tests.
