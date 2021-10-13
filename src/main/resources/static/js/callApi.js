/*
 * options : {
   url: 'request path'
   method: 'POST / GET',
   body: object (request body)
 }
 * callback(response)
 */
const callApi = (options, callback) => {
  if (window.XMLHttpRequest) {
    const httpRequest = new XMLHttpRequest();
    const cookie = document.cookie
      ?.split('; ')
      ?.find(row => row.startsWith('shoppingmall-cookie'))
      ?.split('=')[1];

    httpRequest.responseType = 'json';
    httpRequest.open(options.method, options.url);
    httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpRequest.setRequestHeader('Authorization', cookie)
    httpRequest.send(JSON.stringify(options.body));

    httpRequest.onreadystatechange = function () {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
        callback(httpRequest.response, httpRequest.status, httpRequest);
      }
    }
  }
}