var customerView = "/customerView/customerView.html";
var operatorView = "/operatorView/operatorView.html";

var init = function () {
    twoWayLoginEvents();
};

var twoWayLoginEvents = function () {
    var customerRadioBtn = document.getElementById("custRadio");
    var customerSubBtn = document.getElementById("custSub");
    var operatorRadioBtn = document.getElementById("opRadio");
    var operatorSubBtn = document.getElementById("opSub");
    var customerForm = document.getElementById("custForm");
    var operatorForm = document.getElementById("opForm");

    customerRadioBtn.onchange = function () {
        operatorRadioBtn.checked = !this.checked;
        operatorSubBtn.disabled = this.checked;
        customerSubBtn.disabled = !this.checked;

    };
    operatorRadioBtn.onchange = function () {
        customerRadioBtn.checked = !this.checked;
        customerSubBtn.disabled = this.checked;
        operatorSubBtn.disabled = !this.checked;

    };

    customerForm.onsubmit = function () {
        var customerId = $('#customerId').val();
        window.location = window.location.href + (customerView + "?customerId=" + customerId);
        return false;
    };

    operatorForm.onsubmit = function () {
        window.location = window.location.href + (operatorView);
        return false;
    };
};


//Initialisation call
$('document').ready(function () {
    init();
});
