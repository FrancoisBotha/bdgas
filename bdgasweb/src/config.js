const basePortNo = '7001';
const protocol = 'http';
const domain = 'localhost';

function getBaseUrl() {
  if (basePortNo == 80) {
    return protocol + "://" + domain;
  } else {
    return protocol + "://" + domain + ":" + basePortNo;
  }  
}

export default {
    BASE_PORT_NO: basePortNo,
    PROTOCOL: protocol,
    DOMAIN: domain,
    BASE_URL: getBaseUrl(),
    HOME_URL: getBaseUrl() + '/ui/welcome',
    AWS_GETSIGNEDURL_ENDPOINT: getBaseUrl() + '/api/v1/s3/signedurl',
    DATASOURCE_ENDPOINT: getBaseUrl() + '/api/v1/datasource'
  }