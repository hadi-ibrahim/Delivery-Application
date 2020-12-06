$(document).ready(function () {
    navigator.geolocation.getCurrentPosition(
        successCallback,
        errorCallback_highAccuracy,
        { maximumAge: 10000, timeout: 15000, enableHighAccuracy: true }
    );

    function successCallback(position) {
        var $latitude = position.coords.latitude;
        var $longitude = position.coords.longitude;
        var $accuracy = position.coords.accuracy;
        $('#flag').val("true");
        $('#accuracy').val($accuracy);
        $('#latitude').val($latitude);
        $('#longitude').val($longitude);
    }
    function errorCallback_highAccuracy(error) {
        if (error.code == error.TIMEOUT) {
            navigator.geolocation.getCurrentPosition(
                successCallback,
                errorCallback_lowAccuracy,
                { maximumAge: 10000, timeout: 15000, enableHighAccuracy: false });
            return;
        }
        var msg = "<p>Can't get your location (high accuracy attempt). Error = ";
        if (error.code == 1)
            msg += "PERMISSION_DENIED";
        else if (error.code == 2)
            msg += "POSITION_UNAVAILABLE";
        msg += ", msg = " + error.message;
        $('body').append(msg);
    };

    function errorCallback_lowAccuracy(error) {
        var msg = "<p>Can't get your location (low accuracy attempt). Error = ";
        if (error.code == 1)
            msg += "PERMISSION_DENIED";
        else if (error.code == 2)
            msg += "POSITION_UNAVAILABLE";
        else if (error.code == 3)
            msg += "TIMEOUT";
        msg += ", msg = " + error.message;
        $('body').append(msg);
    }

})