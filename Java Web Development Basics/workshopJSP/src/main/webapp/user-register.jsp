<%--
  Created by IntelliJ IDEA.
  User: Jo
  Date: 7.4.2020 г.
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>West Compass</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
<body>
<div class="container-fluid">
    <nav class="navbar navbar-expand-lg navbar-background">
        <a class="nav-link text-white active h5" href="/index">Home</a>
        <div class="collapse navbar-collapse d-flex justify-content-end">
            <ul class="navbar-nav row">
                <li class="nav-item col-md-4">
                    <a class="nav-link text-white active font-weight-bold" href="/users/login">Login</a>
                </li>
                <li class="nav-item col-md-4">
                    <a class="nav-link text-white active font-weight-bold" href="/users/register">Register</a>
                </li>
            </ul>
        </div>
    </nav>
    <h2 class="text-center text-white mt-5">Register</h2>
    <form class="mx-auto w-15" method="post">
        <br/>
        <div class="row">
            <div class="col col-md-3"></div>
            <div class="col col-md-3">
                <div class="form-group">
                    <div class="label-holder d-flex justify-content-center">
                        <label class="text-center text-white font-weight-bold">Username
                            <input type="text" class="form-control" name="username" placeholder="Username">
                        </label>
                    </div>
                </div>
            </div>
            <div class="col col-md-3">
                <div class="form-group">
                    <div class="label-holder d-flex justify-content-center">
                        <label class="text-center text-white font-weight-bold">Password
                            <input type="password" class="form-control" name="password" placeholder="Password">
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col col-md-3"></div>
            <div class="col col-md-3">
                <div class="form-group">
                    <div class="label-holder d-flex justify-content-center">
                        <label class="text-center text-white font-weight-bold">Confirm Password
                            <input type="password" class="form-control" placeholder="Confirm Password"
                                   name="confirmPassword">
                        </label>
                    </div>
                </div>
            </div>
            <div class="col col-md-3">
                <div class="form-group ">
                    <div class="label-holder d-flex justify-content-center">
                        <label class="text-center text-white font-weight-bold">Email
                            <input type="text" class="form-control" name="email" placeholder="Email">
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col col-md-4"></div>
            <div class="col col-md-4">
                <div class="button-holder d-flex justify-content-center">
                    <input type="submit" class="btn btn-secondary" value="Register"/>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
