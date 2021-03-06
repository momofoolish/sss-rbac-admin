// 这里的正则表达式均在hutool工具中提取，被使用到的可以正常使用

export const RULE_GENERAL = "^\\w+$";
export const RULE_NUMBERS = "\\d+";
export const RULE_WORD = "[a-zA-Z]+";
export const RULE_CHINESE = "[一-\u9fff]";
export const RULE_CHINESES = "[一-\u9fff]+";
export const RULE_GROUP_VAR = "\\$(\\d+)";
export const RULE_IPV4 = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)$";
export const RULE_IPV6 = "(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+|::(ffff(:0{1,4})?:)?((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9]))";
export const RULE_MONEY = "^(\\d+(?:\\.\\d+)?)$";
export const RULE_EMAIL = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
export const RULE_MOBILE = /(?:0|86|\+86)?1[3-9]\d{9}/;
export const RULE_MOBILE_HK = "(?:0|852|\\+852)?\\d{8}";
export const RULE_MOBILE_TW = "(?:0|886|\\+886)?(?:|-)09\\d{8}";
export const RULE_MOBILE_MO = "(?:0|853|\\+853)?(?:|-)6\\d{7}";
export const RULE_TEL = "(010|02\\d|0[3-9]\\d{2})-?(\\d{6,8})";
export const RULE_TEL_400_800 = "0\\d{2,3}[\\- ]?[1-9]\\d{6,7}|[48]00[\\- ]?[1-9]\\d{6}";
export const RULE_CITIZEN_ID = "[1-9]\\d{5}[1-2]\\d{3}((0\\d)|(1[0-2]))(([012]\\d)|3[0-1])\\d{3}(\\d|X|x)";
export const RULE_ZIP_CODE = "^(0[1-7]|1[0-356]|2[0-7]|3[0-6]|4[0-7]|5[0-7]|6[0-7]|7[0-5]|8[0-9]|9[0-8])\\d{4}|99907[78]$";
export const RULE_BIRTHDAY = "^(\\d{2,4})([/\\-.年]?)(\\d{1,2})([/\\-.月]?)(\\d{1,2})日?$";
export const RULE_URI = "^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?";
export const RULE_URL = "[a-zA-Z]+://[\\w-+&@#/%?=~_|!:,.;]*[\\w-+&@#/%=~_|]";
export const RULE_URL_HTTP = "(https?|ftp|file)://[\\w-+&@#/%?=~_|!:,.;]*[\\w-+&@#/%=~_|]";
export const RULE_GENERAL_WITH_CHINESE = "^[一-\u9fff\\w]+$";
export const RULE_UUID = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
export const RULE_UUID_SIMPLE = "^[0-9a-fA-F]{32}$";
export const RULE_MAC_ADDRESS = "((?:[a-fA-F0-9]{1,2}[:-]){5}[a-fA-F0-9]{1,2})|0x(\\d{12}).+ETHER";
export const RULE_HEX = "^[a-fA-F0-9]+$";
export const RULE_TIME = "\\d{1,2}:\\d{1,2}(:\\d{1,2})?";
export const RULE_PLATE_NUMBER = "^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[ABCDEFGHJK])|([ABCDEFGHJK]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]\\d{3}\\d{1,3}[领])|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$";
export const RULE_CREDIT_CODE = "^[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}$";
export const RULE_CAR_VIN = "^[A-Za-z0-9]{17}$";
export const RULE_CAR_DRIVING_LICENCE = "^[0-9]{12}$";