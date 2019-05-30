
axios.defaults.baseURL = '/';
axios.defaults.timeout = 60000;
axios.defaults.headers.common['Content-Type'] = 'application/json';
axios.interceptors.response.use(function (res) {
    return Promise.resolve(res);
}, function (err) {
    alert('오류가 발생하였습니다.');
    console.error(err);
    return Promise.reject(err);
});



var goBtn = document.querySelector('button#goBtn');

var urlInput = document.querySelector('input[name="targetUrl"]');
var mokInput = document.querySelector('input[name="mok"]');
var conditionSelector = document.querySelector('select[name="condition"]');
var divideResultTextarea = document.querySelector('textarea#divideResult');
var remainResultTextarea = document.querySelector('textarea#remainResult');


goBtn.onclick = function(){

    var param = {
        targetUrl : urlInput.value
        , condition : conditionSelector.value
        , mok : mokInput.value
    }


    axios.post('/alphaNumeric' , param)
        .then( function(res){
            var data = res.data;

            divideResultTextarea.innerText = data.divideValue;
            remainResultTextarea.innerText = data.remainValue;
        });
}

