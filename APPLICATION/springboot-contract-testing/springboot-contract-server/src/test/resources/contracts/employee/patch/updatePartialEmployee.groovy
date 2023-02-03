package contracts.employee.get;

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should update and return employee"

    request {
        method PATCH()
        headers {
            contentType(applicationJson())
        }
        body (file("employee.json"))
        url "/employee/1"
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body (file("employee.json"))
    }
}