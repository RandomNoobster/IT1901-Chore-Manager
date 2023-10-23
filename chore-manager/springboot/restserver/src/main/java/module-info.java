module main.springboot.rest {
    requires spring.web;
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.boot.autoconfigure;

    requires main.core;
    requires main.persistence;

    opens springboot.restserver to spring.beans, spring.context, spring.web;
}
