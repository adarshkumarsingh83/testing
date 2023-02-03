package contracts.employee.get;

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return employee by id=1"

    request {
        url "/employee/1"
        method DELETE()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body (file("employee.json"))
    }
}