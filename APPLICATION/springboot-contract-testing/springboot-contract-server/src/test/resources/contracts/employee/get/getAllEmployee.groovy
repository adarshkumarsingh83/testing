package contracts.employee.get;

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return employees "

    request {
        url "/employees"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body (file("employees.json"))
    }
}