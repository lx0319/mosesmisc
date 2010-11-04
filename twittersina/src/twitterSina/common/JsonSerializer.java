/*
 *  Copyright (c) 2009, Steven Wang
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  twitterSina at http://twitterSina.appspot.com
 *  twitterSina code at http://twitterSina.googlecode.com
 * 	
 */
package twitterSina.common;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Json处理类
 * @author Steven Wang <http://steven-wang.appspot.com>
 */
public class JsonSerializer 
{
	/**
	* 将对象序列化成Json字符串
	* @param object，要序列化的对象
	* @return，序列化后的Json字符串
	*/
	public static String serialize(Object object)
	{
		if(object instanceof List)
		{
			return JSONArray.fromObject(object, new JsonConfig()).toString();
		}
		else
		{
			return JSONObject.fromObject(object,new JsonConfig()).toString();
		}
	}
	
	/**
	* 将Json字符串反序列化成对象
	* @param object，Json字符串
	* @param objectClass，对象的类型
	* @return，反序列化后的对象
	*/
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(String object, Class objectClass)
	{
		if(object.charAt(0) == '[')
		{
			JSONArray jsonArray = JSONArray.fromObject(object);   
			JSONObject jsonObject;   
			Object objectValue;   
			List list = new ArrayList();   
			for (int i = 0;i < jsonArray.size();i++) 
			{   
				jsonObject = jsonArray.getJSONObject(i);
				objectValue = JSONObject.toBean(jsonObject, objectClass);
				list.add(objectValue);
			}   
			return (T)list;
		}
		else
		{
			return (T)JSONObject.toBean(JSONObject.fromObject(object), objectClass);
		}
	}
}
