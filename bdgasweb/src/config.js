const localMode = false;  //Wether or local (devl/testing) data is used
const env       = "prod"

const basePortNoProd = '443';
const protocolProd = 'https';
const domainProd = 'auditanalytics.cloud';

const basePortNoDevl = '7001';
const protocolDevl = 'http';
const domainDevl = 'localhost';

function getBaseUrl() {
  
  if (env == "prod") {
    let basePortNo = basePortNoProd
    let protocol = protocolProd
    let domain = domainProd
  } else {
    let basePortNo = basePortNoDevl
    let protocol = protocolDevl
    let domain = domainDevl
  }

  if (basePortNo == 80
    || basePortNo == 443) {
    return protocol + "://" + domain;
  } else {
    return protocol + "://" + domain + ":" + basePortNo;
  }  
}

export default {
    LOCAL_MODE: localMode, 
    BASE_PORT_NO: basePortNo,
    PROTOCOL: protocol,
    DOMAIN: domain,
    BASE_URL: getBaseUrl(),
    HOME_URL: getBaseUrl() + '/ui/welcome',
    LOGOUT_URL: getBaseUrl() + '/logout',
    AWS_GETSIGNEDURL_ENDPOINT: getBaseUrl() + '/api/v1/s3/signedurl',
    DATASOURCE_ENDPOINT: getBaseUrl() + '/api/v1/datasource',
    LOCALDATASOURCE_ENDPOINT: getBaseUrl() + '/api/v1/localdatasource',
    WPLINE_ENDPOINT: getBaseUrl() + '/api/v1/wpline',

    GENERAL_SERVER_ERR_MSG: "An error occurred while processing your request on the server"
  }