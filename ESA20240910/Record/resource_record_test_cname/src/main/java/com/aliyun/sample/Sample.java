// This file is auto-generated, don't edit it. Thanks.
package com.aliyun.sample;

import com.aliyun.tea.*;
import com.aliyun.credentials.*;
import com.aliyun.esa20240910.*;
import com.aliyun.esa20240910.models.*;
import com.aliyun.teautil.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;

public class Sample {

    public Sample() throws Exception {
    }


    /**
     * <b>description</b> :
     * <p>初始化账号 Client</p>
     */
    public static com.aliyun.esa20240910.Client createESA20240910Client() throws Exception {
        Config config = new Config();
        config.credential = new com.aliyun.credentials.Client();
        // Endpoint 请参考 https://api.aliyun.com/product/ESA
        config.endpoint = "esa.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.esa20240910.Client(config);
    }

    /**
     * <b>description</b> :
     * <p>调用接口 PurchaseRatePlan 新购套餐</p>
     */
    public static PurchaseRatePlanResponseBody ratePlanInst(com.aliyun.esa20240910.Client client) throws Exception {
        System.out.println("[CloudSpec CodeSample] 开始调用接口 PurchaseRatePlan 创建资源");
        PurchaseRatePlanRequest purchaseRatePlanRequest = new PurchaseRatePlanRequest()
                .setType("NS")
                .setChargeType("PREPAY")
                .setAutoRenew(false)
                .setPeriod(1)
                .setCoverage("overseas")
                .setAutoPay(true)
                .setPlanName("high");
        PurchaseRatePlanResponse purchaseRatePlanResponse = client.purchaseRatePlan(purchaseRatePlanRequest);
        DescribeRatePlanInstanceStatusRequest describeRatePlanInstanceStatusRequest = new DescribeRatePlanInstanceStatusRequest()
                .setInstanceId(purchaseRatePlanResponse.body.instanceId);
        int currentRetry = 0;
        int delayedTime = 10000;
        int interval = 10000;

        while (currentRetry < 10) {
            try {
                int sleepTime = 0;
                if (currentRetry == 0) {
                    sleepTime = delayedTime;
                } else {
                    sleepTime = interval;
                }

                System.out.println("[CloudSpec CodeSample] 异步检查中");
                Thread.sleep(sleepTime);
            } catch (TeaException error) {
                throw new TeaException(TeaConverter.buildMap(
                    new TeaPair("message", error.message)
                ));
            }            
            DescribeRatePlanInstanceStatusResponse describeRatePlanInstanceStatusResponse = client.describeRatePlanInstanceStatus(describeRatePlanInstanceStatusRequest);
            String instanceStatus = describeRatePlanInstanceStatusResponse.body.instanceStatus;
            if (instanceStatus.equals("running")) {
                System.out.println("[CloudSpec CodeSample] 调用接口 PurchaseRatePlan 新购套餐 成功, 当前 response 为: ");
                System.out.println(com.aliyun.teautil.Common.toJSONString(purchaseRatePlanResponse));
                return purchaseRatePlanResponse.body;
            }

            currentRetry++;
        }
        throw new TeaException(TeaConverter.buildMap(
            new TeaPair("message", "[CloudSpec CodeSample] 异步检查失败")
        ));
    }

    /**
     * <b>description</b> :
     * <p>调用接口 CreateSite 创建站点</p>
     */
    public static CreateSiteResponseBody site(PurchaseRatePlanResponseBody ratePlanInstResponseBody, com.aliyun.esa20240910.Client client) throws Exception {
        System.out.println("[CloudSpec CodeSample] 开始调用接口 CreateSite 创建资源");
        CreateSiteRequest createSiteRequest = new CreateSiteRequest()
                .setSiteName("idltestrecord.com")
                .setInstanceId(ratePlanInstResponseBody.instanceId)
                .setCoverage("overseas")
                .setAccessType("NS");
        CreateSiteResponse createSiteResponse = client.createSite(createSiteRequest);
        GetSiteRequest getSiteRequest = new GetSiteRequest()
                .setSiteId(createSiteResponse.body.siteId);
        int currentRetry = 0;
        int delayedTime = 60000;
        int interval = 10000;

        while (currentRetry < 5) {
            try {
                int sleepTime = 0;
                if (currentRetry == 0) {
                    sleepTime = delayedTime;
                } else {
                    sleepTime = interval;
                }

                System.out.println("[CloudSpec CodeSample] 异步检查中");
                Thread.sleep(sleepTime);
            } catch (TeaException error) {
                throw new TeaException(TeaConverter.buildMap(
                    new TeaPair("message", error.message)
                ));
            }            
            GetSiteResponse getSiteResponse = client.getSite(getSiteRequest);
            String status = getSiteResponse.body.siteModel.status;
            if (status.equals("pending")) {
                System.out.println("[CloudSpec CodeSample] 调用接口 CreateSite 创建站点 成功, 当前 response 为: ");
                System.out.println(com.aliyun.teautil.Common.toJSONString(createSiteResponse));
                return createSiteResponse.body;
            }

            currentRetry++;
        }
        throw new TeaException(TeaConverter.buildMap(
            new TeaPair("message", "[CloudSpec CodeSample] 异步检查失败")
        ));
    }

    /**
     * <b>description</b> :
     * <p>调用接口 CreateRecord 创建记录</p>
     */
    public static CreateRecordResponseBody recordCname(CreateSiteResponseBody siteResponseBody, com.aliyun.esa20240910.Client client) throws Exception {
        System.out.println("[CloudSpec CodeSample] 开始调用接口 CreateRecord 创建资源");
        CreateRecordRequest.CreateRecordRequestAuthConf authConf = new CreateRecordRequest.CreateRecordRequestAuthConf()
                .setSecretKey("hijklmnhij*********mnhijklmn")
                .setVersion("v4")
                .setRegion("us-east-1")
                .setAuthType("private")
                .setAccessKey("abcdefgabcdefgabcdefgabcdefg");
        CreateRecordRequest.CreateRecordRequestData data = new CreateRecordRequest.CreateRecordRequestData()
                .setValue("www.idltestr.com");
        CreateRecordRequest createRecordRequest = new CreateRecordRequest()
                .setRecordName("www.idltestrecord.com")
                .setComment("This is a remark")
                .setProxied(true)
                .setSiteId(siteResponseBody.siteId)
                .setType("CNAME")
                .setSourceType("S3")
                .setData(data)
                .setBizName("api")
                .setHostPolicy("follow_hostname")
                .setTtl(100)
                .setAuthConf(authConf);
        CreateRecordResponse createRecordResponse = Sample.createRecordWithRetry(client, createRecordRequest);
        System.out.println("[CloudSpec CodeSample] 调用接口 CreateRecord 创建记录成功, 当前 response: ");
        System.out.println(com.aliyun.teautil.Common.toJSONString(createRecordResponse));
        return createRecordResponse.body;
    }

    public static CreateRecordResponse createRecordWithRetry(com.aliyun.esa20240910.Client client, CreateRecordRequest createRecordRequest) throws Exception {
        String errorCode = "";
        int currentRetry = 0;
        int interval = 5000;

        while (currentRetry < 10) {
            try {
                CreateRecordResponse createRecordResponse = client.createRecord(createRecordRequest);
                System.out.println("[CloudSpec CodeSample] 调用接口 CreateRecord 创建记录 成功, 当前 response 为: ");
                System.out.println(com.aliyun.teautil.Common.toJSONString(createRecordResponse));
                return createRecordResponse;
            } catch (TeaException error) {
                errorCode = error.code;
            }            
            if (errorCode.equals("Site.ServiceBusy")) {
                System.out.println("[CloudSpec CodeSample] 调用接口 CreateRecord 失败, 错误码 Site.ServiceBusy, 重试");
                Thread.sleep(interval);
                currentRetry++;
            }

        }
        throw new TeaException(TeaConverter.buildMap(
            new TeaPair("message", "[CloudSpec CodeSample] 调用接口 CreateRecord 创建记录 失败")
        ));
    }

    /**
     * <b>description</b> :
     * <p>调用接口 UpdateRecord 更新记录</p>
     */
    public static void updateRecordCname(CreateRecordResponseBody createRecordResponseBody, com.aliyun.esa20240910.Client client) throws Exception {
        System.out.println("[CloudSpec CodeSample] 开始调用接口 UpdateRecord 更新资源");
        UpdateRecordRequest.UpdateRecordRequestAuthConf authConf = new UpdateRecordRequest.UpdateRecordRequestAuthConf()
                .setSecretKey("hijklmnhij*********mnhijklmn")
                .setVersion("v4")
                .setRegion("us-east-1")
                .setAuthType("private")
                .setAccessKey("abcdefgabcdefgabcdefgabcdefg");
        UpdateRecordRequest.UpdateRecordRequestData data = new UpdateRecordRequest.UpdateRecordRequestData()
                .setValue("www.idltestr.com");
        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest()
                .setComment("This is a remark")
                .setProxied(true)
                .setSourceType("S3")
                .setData(data)
                .setBizName("web")
                .setHostPolicy("follow_hostname")
                .setTtl(100)
                .setAuthConf(authConf)
                .setRecordId(createRecordResponseBody.recordId);
        UpdateRecordResponse updateRecordResponse = Sample.updateRecordWithRetry(client, updateRecordRequest);
        System.out.println("[CloudSpec CodeSample] 调用接口 UpdateRecord 更新记录成功, 当前 response: ");
        System.out.println(com.aliyun.teautil.Common.toJSONString(updateRecordResponse));
    }

    /**
     * <b>description</b> :
     * <p>调用接口 UpdateRecord 更新记录</p>
     */
    public static void updateRecordCname1(CreateRecordResponseBody createRecordResponseBody, com.aliyun.esa20240910.Client client) throws Exception {
        System.out.println("[CloudSpec CodeSample] 开始调用接口 UpdateRecord 更新资源");
        UpdateRecordRequest.UpdateRecordRequestAuthConf authConf = new UpdateRecordRequest.UpdateRecordRequestAuthConf()
                .setSecretKey("hijklmnhij*********mnhijklmn")
                .setVersion("v4")
                .setRegion("us-east-1")
                .setAuthType("private")
                .setAccessKey("abcdefgabcdefgabcdefgabcdefg");
        UpdateRecordRequest.UpdateRecordRequestData data = new UpdateRecordRequest.UpdateRecordRequestData()
                .setValue("www.pangleitestupdate.com");
        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest()
                .setComment("This is a remark")
                .setProxied(true)
                .setSourceType("S3")
                .setData(data)
                .setBizName("web")
                .setHostPolicy("follow_hostname")
                .setTtl(100)
                .setAuthConf(authConf)
                .setRecordId(createRecordResponseBody.recordId);
        UpdateRecordResponse updateRecordResponse = Sample.updateRecordWithRetry(client, updateRecordRequest);
        System.out.println("[CloudSpec CodeSample] 调用接口 UpdateRecord 更新记录成功, 当前 response: ");
        System.out.println(com.aliyun.teautil.Common.toJSONString(updateRecordResponse));
    }

    /**
     * <b>description</b> :
     * <p>调用接口 UpdateRecord 更新记录</p>
     */
    public static void updateRecordCname2(CreateRecordResponseBody createRecordResponseBody, com.aliyun.esa20240910.Client client) throws Exception {
        System.out.println("[CloudSpec CodeSample] 开始调用接口 UpdateRecord 更新资源");
        UpdateRecordRequest.UpdateRecordRequestAuthConf authConf = new UpdateRecordRequest.UpdateRecordRequestAuthConf()
                .setSecretKey("hijklmnhij*********mnhijklmn")
                .setVersion("v4")
                .setRegion("us-east-1")
                .setAuthType("private")
                .setAccessKey("abcdefgabcdefgabcdefgabcdefg");
        UpdateRecordRequest.UpdateRecordRequestData data = new UpdateRecordRequest.UpdateRecordRequestData()
                .setValue("www.pangleitestupdate.com");
        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest()
                .setComment("This is a remark")
                .setProxied(true)
                .setSourceType("S3")
                .setData(data)
                .setBizName("web")
                .setHostPolicy("follow_hostname")
                .setTtl(3600)
                .setAuthConf(authConf)
                .setRecordId(createRecordResponseBody.recordId);
        UpdateRecordResponse updateRecordResponse = Sample.updateRecordWithRetry(client, updateRecordRequest);
        System.out.println("[CloudSpec CodeSample] 调用接口 UpdateRecord 更新记录成功, 当前 response: ");
        System.out.println(com.aliyun.teautil.Common.toJSONString(updateRecordResponse));
    }

    /**
     * <b>description</b> :
     * <p>调用接口 UpdateRecord 更新记录</p>
     */
    public static void updateRecordCname3(CreateRecordResponseBody createRecordResponseBody, com.aliyun.esa20240910.Client client) throws Exception {
        System.out.println("[CloudSpec CodeSample] 开始调用接口 UpdateRecord 更新资源");
        UpdateRecordRequest.UpdateRecordRequestAuthConf authConf = new UpdateRecordRequest.UpdateRecordRequestAuthConf()
                .setSecretKey("hijklmnhij*********mnhijklmn")
                .setVersion("v4")
                .setRegion("us-east-1")
                .setAuthType("private")
                .setAccessKey("abcdefgabcdefgabcdefgabcdefg");
        UpdateRecordRequest.UpdateRecordRequestData data = new UpdateRecordRequest.UpdateRecordRequestData()
                .setValue("www.pangleitestupdate.com");
        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest()
                .setComment("DNS记录测试")
                .setProxied(true)
                .setSourceType("S3")
                .setData(data)
                .setBizName("web")
                .setHostPolicy("follow_hostname")
                .setTtl(3600)
                .setAuthConf(authConf)
                .setRecordId(createRecordResponseBody.recordId);
        UpdateRecordResponse updateRecordResponse = Sample.updateRecordWithRetry(client, updateRecordRequest);
        System.out.println("[CloudSpec CodeSample] 调用接口 UpdateRecord 更新记录成功, 当前 response: ");
        System.out.println(com.aliyun.teautil.Common.toJSONString(updateRecordResponse));
    }

    /**
     * <b>description</b> :
     * <p>调用接口 UpdateRecord 更新记录</p>
     */
    public static void updateRecordCname4(CreateRecordResponseBody createRecordResponseBody, com.aliyun.esa20240910.Client client) throws Exception {
        System.out.println("[CloudSpec CodeSample] 开始调用接口 UpdateRecord 更新资源");
        UpdateRecordRequest.UpdateRecordRequestAuthConf authConf = new UpdateRecordRequest.UpdateRecordRequestAuthConf()
                .setSecretKey("secretkey12***********defghijklmn")
                .setVersion("v2")
                .setRegion("us-east-2")
                .setAuthType("public")
                .setAccessKey("AccessKey1234567890abcdefghijklmn");
        UpdateRecordRequest.UpdateRecordRequestData data = new UpdateRecordRequest.UpdateRecordRequestData()
                .setValue("www.pangleitestupdate.com");
        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest()
                .setComment("DNS记录测试")
                .setProxied(true)
                .setSourceType("OSS")
                .setData(data)
                .setBizName("web")
                .setHostPolicy("follow_hostname")
                .setTtl(3600)
                .setAuthConf(authConf)
                .setRecordId(createRecordResponseBody.recordId);
        UpdateRecordResponse updateRecordResponse = Sample.updateRecordWithRetry(client, updateRecordRequest);
        System.out.println("[CloudSpec CodeSample] 调用接口 UpdateRecord 更新记录成功, 当前 response: ");
        System.out.println(com.aliyun.teautil.Common.toJSONString(updateRecordResponse));
    }

    /**
     * <b>description</b> :
     * <p>调用接口 UpdateRecord 更新记录</p>
     */
    public static void updateRecordCname5(CreateRecordResponseBody createRecordResponseBody, com.aliyun.esa20240910.Client client) throws Exception {
        System.out.println("[CloudSpec CodeSample] 开始调用接口 UpdateRecord 更新资源");
        UpdateRecordRequest.UpdateRecordRequestAuthConf authConf = new UpdateRecordRequest.UpdateRecordRequestAuthConf()
                .setSecretKey("secretkey09**********dcbafedcba")
                .setVersion("v2")
                .setRegion("us-gov-west-1")
                .setAuthType("private")
                .setAccessKey("AccessKey0987654321fedcbafedcba");
        UpdateRecordRequest.UpdateRecordRequestData data = new UpdateRecordRequest.UpdateRecordRequestData()
                .setValue("www.plexample.com");
        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest()
                .setComment("test_record_comment")
                .setProxied(true)
                .setSourceType("S3")
                .setData(data)
                .setBizName("api")
                .setHostPolicy("follow_origin_domain")
                .setTtl(86400)
                .setAuthConf(authConf)
                .setRecordId(createRecordResponseBody.recordId);
        UpdateRecordResponse updateRecordResponse = Sample.updateRecordWithRetry(client, updateRecordRequest);
        System.out.println("[CloudSpec CodeSample] 调用接口 UpdateRecord 更新记录成功, 当前 response: ");
        System.out.println(com.aliyun.teautil.Common.toJSONString(updateRecordResponse));
    }

    public static UpdateRecordResponse updateRecordWithRetry(com.aliyun.esa20240910.Client client, UpdateRecordRequest updateRecordRequest) throws Exception {
        String errorCode = "";
        int currentRetry = 0;
        int interval = 3000;

        while (currentRetry < 10) {
            try {
                UpdateRecordResponse updateRecordResponse = client.updateRecord(updateRecordRequest);
                System.out.println("[CloudSpec CodeSample] 调用接口 UpdateRecord 更新记录 成功, 当前 response 为: ");
                System.out.println(com.aliyun.teautil.Common.toJSONString(updateRecordResponse));
                return updateRecordResponse;
            } catch (TeaException error) {
                errorCode = error.code;
            }            
            if (errorCode.equals("Record.ServiceBusy")) {
                System.out.println("[CloudSpec CodeSample] 调用接口 UpdateRecord 失败, 错误码 Record.ServiceBusy, 重试");
                Thread.sleep(interval);
                currentRetry++;
            }

        }
        throw new TeaException(TeaConverter.buildMap(
            new TeaPair("message", "[CloudSpec CodeSample] 调用接口 UpdateRecord 更新记录 失败")
        ));
    }

    /**
     * <b>description</b> :
     * <p>调用接口 DeleteRecord 删除记录</p>
     */
    public static void destroyRecordCname(CreateRecordResponseBody createRecordResponseBody, com.aliyun.esa20240910.Client client) throws Exception {
        System.out.println("[CloudSpec CodeSample] 开始调用接口 DeleteRecord 删除资源");
        DeleteRecordRequest deleteRecordRequest = new DeleteRecordRequest()
                .setRecordId(createRecordResponseBody.recordId);
        DeleteRecordResponse deleteRecordResponse = Sample.deleteRecordWithRetry(client, deleteRecordRequest);
        System.out.println("[CloudSpec CodeSample] 调用接口 DeleteRecord 删除记录 成功, 当前 response 为: ");
        System.out.println(com.aliyun.teautil.Common.toJSONString(deleteRecordResponse));
    }

    public static DeleteRecordResponse deleteRecordWithRetry(com.aliyun.esa20240910.Client client, DeleteRecordRequest deleteRecordRequest) throws Exception {
        String errorCode = "";
        int currentRetry = 0;
        int interval = 1000;

        while (currentRetry < 10) {
            try {
                DeleteRecordResponse deleteRecordResponse = client.deleteRecord(deleteRecordRequest);
                System.out.println("[CloudSpec CodeSample] 调用接口 DeleteRecord 删除记录 成功, 当前 response 为: ");
                System.out.println(com.aliyun.teautil.Common.toJSONString(deleteRecordResponse));
                return deleteRecordResponse;
            } catch (TeaException error) {
                errorCode = error.code;
            }            
            if (errorCode.equals("Record.ServiceBusy")) {
                System.out.println("[CloudSpec CodeSample] 调用接口 DeleteRecord 失败, 错误码 Record.ServiceBusy, 重试");
                Thread.sleep(interval);
                currentRetry++;
            }

        }
        throw new TeaException(TeaConverter.buildMap(
            new TeaPair("message", "[CloudSpec CodeSample] 调用接口 DeleteRecord 删除记录 失败")
        ));
    }

    /**
     * <b>description</b> :
     * <p>运行代码可能会影响当前账号的线上资源，请务必谨慎操作！</p>
     */
    public static void main(String[] args) throws Exception {
        // 代码包含涉及到费用的接口，请您确保在使用该接口前，已充分了解收费方式和价格。
        // 设置环境变量 COST_ACK 为 true 或删除下列判断即可运行示例代码
        String costAcknowledged = System.getenv("COST_ACK");
        if ((null == costAcknowledged) || !costAcknowledged.equals("true")) {
            System.out.println("代码中的 PurchaseRatePlan 接口涉及到费用，请谨慎操作！");
            return ;
        }

        // 初始化 Client
        com.aliyun.esa20240910.Client esa20240910Client = Sample.createESA20240910Client();
        // 初始化资源
        PurchaseRatePlanResponseBody ratePlanInstRespBody = Sample.ratePlanInst(esa20240910Client);
        CreateSiteResponseBody siteRespBody = Sample.site(ratePlanInstRespBody, esa20240910Client);
        // 测试Record资源，记录类型为CNAME
        CreateRecordResponseBody recordCnameRespBody = Sample.recordCname(siteRespBody, esa20240910Client);
        // 更新资源
        Sample.updateRecordCname(recordCnameRespBody, esa20240910Client);
        Sample.updateRecordCname1(recordCnameRespBody, esa20240910Client);
        Sample.updateRecordCname2(recordCnameRespBody, esa20240910Client);
        Sample.updateRecordCname3(recordCnameRespBody, esa20240910Client);
        Sample.updateRecordCname4(recordCnameRespBody, esa20240910Client);
        Sample.updateRecordCname5(recordCnameRespBody, esa20240910Client);
        // 删除资源
        Sample.destroyRecordCname(recordCnameRespBody, esa20240910Client);
    }
}
