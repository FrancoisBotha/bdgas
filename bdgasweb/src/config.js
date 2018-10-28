const localMode = false;  //Wether or local (devl/testing) data is used
const env       = "devl"

const bucketName = "bdgassandbox";
const awsRegion = 'eu-west-1';

const basePortNoProd = '443';
const protocolProd = 'https';
const domainProd = 'auditanalytics.cloud';

const basePortNoDevl = '7001';
const protocolDevl = 'http';
const domainDevl = 'localhost';

var basePortNo = ""
var protocol = ""
var domain = ""

function getBaseUrl() {
  
  if (env == "prod") {
    basePortNo = basePortNoProd
    protocol = protocolProd
    domain = domainProd
  } else {
    basePortNo = basePortNoDevl
    protocol = protocolDevl
    domain = domainDevl
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
    BUCKETNAME: bucketName,
    AWSREGION: awsRegion,
    GENERAL_SERVER_ERR_MSG: "An error occurred while processing your request on the server"
  }