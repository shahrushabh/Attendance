/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.iastate.i_attend.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "iAttend",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.i_attend.iastate.com",
                ownerName = "backend.i_attend.iastate.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHello",
                path = "sayHello",
                httpMethod = ApiMethod.HttpMethod.GET)
    public MyBean sayHello() {
        MyBean response = new MyBean();
        response.setData("Hi, Rushabh!");
        return response;
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "get_course",
                path = "get_course",
                httpMethod = ApiMethod.HttpMethod.GET)
    public Course get_course() {
        return new Course(123,"Systems Class");
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "getUser",
            path = "getUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public User getUser(@Named("email") String email) {
//        DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
//        Query userQuery = new Query("User");
//        Query.Filter emailFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
////        Query.Filter emailFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, "rvshah@iastate.edu");
//        userQuery.setFilter(emailFilter);
//        return dataStore.prepare(userQuery).asSingleEntity();
        return new User(123, "Rushabh", "Student", email);
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "addUser",
            path = "addUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void addUser(User user) {
            user = new User(123, "Rushabh", "Student", "testing");
//        return new User(123, "Rushabh", "Student", "testing");
    }
}
