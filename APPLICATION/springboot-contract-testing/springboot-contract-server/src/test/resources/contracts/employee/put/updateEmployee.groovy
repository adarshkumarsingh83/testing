package contracts.employee.post;

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description "create a new employee"

    request {
        method PUT()
        headers {
            contentType applicationJson()
            accept(applicationJson())
        }
        body (file("employee-put-input.json"))
        url "/employee/1"
    }

    response {
        status 200
        headers {
            contentType applicationJson()
        }
        body (file("employee-put-output.json"))
    }
}