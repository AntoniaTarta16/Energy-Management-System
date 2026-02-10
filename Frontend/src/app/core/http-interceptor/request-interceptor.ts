import { HttpHeaders, HttpInterceptorFn } from '@angular/common/http';


export const requestInterceptor: HttpInterceptorFn = (req, next) => {
  const modifiedReq = req.clone({
    url: getUrl(req.url),
    headers: getHeaders(req.url),
    withCredentials: true
  });

  return next(modifiedReq);
};

const getUrl = (url: string): string => {
  if (url.includes('users') || url.includes('auth')) {
    //return 'http://localhost:8537/api/' + url;
    return 'http://users.localhost/api/' + url;
  }
  if (url.includes('measurements')) {
    //return 'http://localhost:8543/api/' + url;
    return 'http://monitoring.localhost/api/' + url;
  }
  if (url.includes('devices')) {
    //return 'http://localhost:8539/api/' + url;
    return 'http://devices.localhost/api/' + url;
  }
  //return 'http://localhost:8547/api/' + url;
  return 'http://chat.localhost/api/' + url;
};

const getHeaders = (url: string): HttpHeaders => {
  const jwtToken = sessionStorage.getItem('jwt-token');

  return url.includes('auth') || jwtToken === ''
    ? new HttpHeaders()
    : new HttpHeaders({ 'Authorization': `Bearer ${jwtToken}` });
};
