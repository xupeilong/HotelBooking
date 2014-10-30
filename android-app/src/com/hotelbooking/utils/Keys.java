/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.hotelbooking.utils;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088411857583121";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "1586928607@qq.com";

	//商户私钥，自助生成
//	public static final String PRIVATE = "MIICXQIBAAKBgQDJ+Lds9A+FyjVaGXAI+j1YNHms2Zs7vKrZsms4oqlQecd3brPk"
// + "OTAT4etiW1/MzVdOf351h2pzqbNQb7t5DuWDRFy6qrn8afNs9FoyplUGisC2cidF"
// + "tURwoPJbg9QsTDTFboOXfS9cROc8ICeRl/pAXzAzPakpAfBQTKIeBVnDTwIDAQAB"
// + "AoGAQq19wNaCGWU8X9ZuCkiy39/TG6FrgjkzHlOs2jG5O/w4QdGmSf4nRSkckIgi"
// + "BHb2D9A/a4WdOzoXHMMGXVzxn+F7dMaCyv/LLOT4z0TuSAwiXrW9IinqES59O+vO"
// + "zFrOvE8S1jHkjF8l7E9fORTeyYcLzEYQa6UOOtJbTqySmCECQQD9On4+VFV3sQeA"
// + "LGppW7wY6UNjO5ODx+8vQhDgJiYE/3yVlDx1BeHEzZxm1GK6kwDCDNLwnLGpJo9H"
// + "uqZKCehDAkEAzC6cAPdn+AH5Wo3PFIlxIX8Zy58ShKPMjEI5ZkMzves50qaAgefm"
// + "1sdflBU5gz54YuiOC64Gi6Ti8l+fZPg+BQJAdnWgjQPin4VfHuSoOvVA/t5QOeGq"
// + "1mD3UUI0psbxzjC+VTLtPUktHmWZHrmIHgOavSxGmWyT5680I7f7BnOwBQJBAIVA"
// + "Pctbqtxt3V+9gFF0yQIFQr5iV+F8THeUmIUmglQFeT0CMIeW4k102+l2WIxQbpyO"
// + "DHWO4vKrbviicxGf+EkCQQCQZe7nQjWki8zSw+gfNeUPVkXrautfaUEj/puSwxcz"
// + "Ktsl90DgtMRLypr8FrUBOTCqB2VYYz73Emh57foP4OV7";
	
	public static final String PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMn4t2z0D4XKNVoZ"
	+ "cAj6PVg0eazZmzu8qtmyaziiqVB5x3dus+Q5MBPh62JbX8zNV05/fnWHanOps1Bv"
	+ "u3kO5YNEXLqqufxp82z0WjKmVQaKwLZyJ0W1RHCg8luD1CxMNMVug5d9L1xE5zwg"
	+ "J5GX+kBfMDM9qSkB8FBMoh4FWcNPAgMBAAECgYBCrX3A1oIZZTxf1m4KSLLf39Mb"
	+ "oWuCOTMeU6zaMbk7/DhB0aZJ/idFKRyQiCIEdvYP0D9rhZ07OhccwwZdXPGf4Xt0"
	+ "xoLK/8ss5PjPRO5IDCJetb0iKeoRLn07687MWs68TxLWMeSMXyXsT185FN7JhwvM"
	+ "RhBrpQ460ltOrJKYIQJBAP06fj5UVXexB4AsamlbvBjpQ2M7k4PH7y9CEOAmJgT/"
	+ "fJWUPHUF4cTNnGbUYrqTAMIM0vCcsakmj0e6pkoJ6EMCQQDMLpwA92f4Aflajc8U"
	+ "iXEhfxnLnxKEo8yMQjlmQzO96znSpoCB5+bWx1+UFTmDPnhi6I4LrgaLpOLyX59k"
	+ "+D4FAkB2daCNA+KfhV8e5Kg69UD+3lA54arWYPdRQjSmxvHOML5VMu09SS0eZZke"
	+ "uYgeA5q9LEaZbJPnrzQjt/sGc7AFAkEAhUA9y1uq3G3dX72AUXTJAgVCvmJX4XxM"
	+ "d5SYhSaCVAV5PQIwh5biTXTb6XZYjFBunI4MdY7i8qtu+KJzEZ/4SQJBAJBl7udC"
	+ "NaSLzNLD6B815Q9WRetq619pQSP+m5LDFzMq2yX3QOC0xEvKmvwWtQE5MKoHZVhj"
	+ "PvcSaHnt+g/g5Xs=";

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJ+Lds9A+FyjVaGXAI+j1YNHms2Zs7vKrZsms4oqlQecd3brPkOTAT4etiW1/MzVdOf351h2pzqbNQb7t5DuWDRFy6qrn8afNs9FoyplUGisC2cidFtURwoPJbg9QsTDTFboOXfS9cROc8ICeRl/pAXzAzPakpAfBQTKIeBVnDTwIDAQAB";

}
