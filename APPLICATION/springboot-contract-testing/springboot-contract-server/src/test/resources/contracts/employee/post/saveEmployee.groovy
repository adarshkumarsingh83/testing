package contracts.employee.post;

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description "create a new employee"

    request {
        method POST()
        headers {
            contentType(applicationJson())
        }
        body (file("employee.json"))
        url "/employee"
    }

    response {
        status 201
        headers {
            contentType(applicationJson())
        }
        body (file("employee.json"))
    }
}