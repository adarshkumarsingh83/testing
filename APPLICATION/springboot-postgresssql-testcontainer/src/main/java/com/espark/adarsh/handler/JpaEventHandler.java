package com.espark.adarsh.handler;


import com.espark.adarsh.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class JpaEventHandler {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @HandleBeforeCreate
    public void preSave(Employee employee){
        logger.info("preSave "+employee);
    }

    @HandleAfterCreate
    public void postSave(Employee employee){
        logger.info("postSave "+employee);
    }

    @HandleBeforeSave
    public void preUpdate(Employee employee){
        logger.info("preUpdate "+employee);
    }

    @HandleAfterSave
    public void postUpdate(Employee employee){
        logger.info("postUpdate "+employee);
    }

    @HandleBeforeDelete
    public void preDelete(Employee employee){
        logger.info("preDelete "+employee);
    }

    @HandleAfterDelete
    public void postDelete(Employee employee){
        logger.info("postDelete "+employee);
    }

}
