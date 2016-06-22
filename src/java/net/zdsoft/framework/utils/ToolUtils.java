package net.zdsoft.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.cglib.beans.BeanMap;
import net.zdsoft.framework.annotation.ColumnInfo;
import net.zdsoft.framework.entity.BaseEntity;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

public class ToolUtils {

	private static final Map<String, Map<String, Map<String, Object>>> columnInfoMap = new HashMap<String, Map<String, Map<String, Object>>>();
	private static final Map<String, List<String>> entityInfoMap = new HashMap<String, List<String>>();

	public static <T extends BaseEntity> T getGeneric(Type type) {
		Type[] generics = ((ParameterizedType) type).getActualTypeArguments();
		@SuppressWarnings("unchecked")
		Class<T> mTClass = (Class<T>) (generics[0]);
		try {
			T t2 = mTClass.newInstance();
			return t2;
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return null;
	}

	public static <T> List<T> changePosition(List<T> list, T t, int upSize) {
		if (upSize == 0) {
			return list;
		}
		int index = list.indexOf(t);
		if (index < 0) {
			return list;
		}
		list.remove(t);
		list.add(index + upSize < 0 ? 0 : index + upSize, t);
		return list;
	}

	private static void initColumnProperties(Class<?> clazz) {
		Class<?> superClass = clazz;
		String key = superClass.getName();
		List<String> infos = entityInfoMap.get(key);
		if (infos == null) {
			List<Object[]> infos2 = new ArrayList<Object[]>();
			infos = new ArrayList<String>();
			entityInfoMap.put(key, infos);

			while (superClass != null) {
				Set<String> set = new HashSet<String>();
				Method[] methods = superClass.getDeclaredMethods();
				for (Method method : methods) {
					if (method.getModifiers() != Modifier.PUBLIC) {
						continue;
					}
					String name = method.getName();
					if (StringUtils.startsWith(name, "get")) {
						set.add(StringUtils.substringAfter(name, "get").toLowerCase());
					}
				}
				Field[] fields = superClass.getDeclaredFields();
				for (Field field : fields) {
					String name = field.getName();
					if (!set.contains(name.toLowerCase()))
						continue;
					ColumnInfo colInfo = field.getAnnotation(ColumnInfo.class);
					if (colInfo == null)
						continue;
					infos2.add(new Object[] { name, colInfo.displayOrder() });
				}
				superClass = superClass.getSuperclass();
			}

			Collections.sort(infos2, new Comparator<Object[]>() {
				@Override
				public int compare(Object[] o1, Object[] o2) {
					Object do1 = o1[1];
					Object do2 = o2[1];
					if (do1 == null)
						return -1;
					if (do2 == null)
						return 1;
					return ((Integer) do1) - ((Integer) do2);
				}
			});

			for (Object[] os : infos2) {
				infos.add((String) os[0]);
			}
		}
	}

	private static void initColumnInfos(Class<?> clazz) {
		String key = clazz.getName();
		Map<String, Map<String, Object>> infos = columnInfoMap.get(key);
		if (infos == null) {
			infos = new LinkedHashMap<String, Map<String, Object>>();
			columnInfoMap.put(key, infos);
			Class<?> superClass = clazz;
			while (superClass != null) {
				Field[] fields = superClass.getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent(ColumnInfo.class)) {
						ColumnInfo p = field.getAnnotation(ColumnInfo.class);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("format", p.format());
						map.put("displayName", p.displayName());
						map.put("mcodeId", p.mcodeId());
						map.put("nullable", p.nullable());
						map.put("vtype", p.vtype());
						map.put("vsql", p.vsql());
						map.put("length", p.length());
						map.put("maxLength", p.maxLength());
						map.put("minLength", p.minLength());
						map.put("max", p.max());
						map.put("min", p.min());
						map.put("regex", p.regex());
						map.put("hide", p.hide());
						map.put("displayOrder", p.displayOrder());
						map.put("disabled", p.disabled());
						infos.put(field.getName(), map);
					}
				}
				superClass = superClass.getSuperclass();
			}
		}
	}

	public static Map<String, Map<String, Object>> getColumnInfos(Class<?> clazz) {
		String key = clazz.getName();
		Map<String, Map<String, Object>> infos = columnInfoMap.get(key);
		if (MapUtils.isEmpty(infos)) {
			initColumnInfos(clazz);
			infos = columnInfoMap.get(key);
		}
		Map<String, Map<String, Object>> ms = new LinkedHashMap<String, Map<String, Object>>();
		ms.putAll(infos);
		return infos;
	}

	public static List<String> getEntityFiledNames(Class<?> clazz) {
		String key = clazz.getName();
		List<String> infos = entityInfoMap.get(key);
		if (infos == null) {
			initColumnProperties(clazz);
			infos = entityInfoMap.get(key);
		}
		List<String> ls = new ArrayList<String>();
		ls.addAll(infos);
		return ls;
	}

	public static Map<String, Object> getColumnInfo(Class<?> clazz, String columnName) {
		return getColumnInfos(clazz).get(columnName);
	}

	public static String[] createArray(String... ss) {
		return ss;
	}

	public static String underline2Camel(String param) {
		param = param.toLowerCase();
		Pattern p = Pattern.compile("_[a-z]");
		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = p.matcher(param);
		while (mc.find()) {
			builder.replace(mc.start(), mc.end(), mc.group().toUpperCase().replaceFirst("_", ""));
			mc = p.matcher(builder.toString());
		}

		if ('_' == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}

	/**
	 * 生成32位UUID
	 * 
	 * @return
	 */
	public static String createUuid() {
		UUID guid = UUID.randomUUID();
		int m = guid.hashCode();
		m = Math.abs(m);
		Long time = System.currentTimeMillis();
		String s = time + "" + m;
		s = s + RandomStringUtils.randomNumeric(32 - s.length());
		return s;
		// return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 类属性复制
	 * 
	 * @param dest
	 * @param ori
	 * @param ignoreNull
	 *                ，如果为true，则将源对象中属性为null的值不进行复制（尽量设置为false，复制效率提高很多）
	 * @return
	 */
	public static <T, K> K copyProperties(T source, K target, boolean ignoreNull) {
		if (source == null)
			return target;
		if (target == null)
			return null;

		if (!ignoreNull)
			return copyProperties(source, target);
		else {
			try {
				BeanMap map = BeanMap.create(target.getClass().newInstance());
				map.setBean(source);

				List<String> getters = new ArrayList<String>();

				for (Object key : map.keySet()) {
					Object v = map.get(key);
					if (v == null) {
						getters.add(key.toString());
					}
				}
				BeanUtils.copyProperties(source, target, getters.toArray(new String[0]));
				return target;

			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	public static <T, K> K copyProperties(T dest, K ori) {
		if (dest == null)
			return ori;
		if (ori == null)
			return null;

		BeanCopier bc = BeanCopier.create(dest.getClass(), ori.getClass(), false);
		try {
			bc.copy(dest, ori, null);
		} catch (Exception e) {
			return null;
		}
		return ori;
	}

	public static <T, K> K copyProperties(T dest, Class<K> classK) {
		if (dest == null)
			return null;
		BeanCopier bc = BeanCopier.create(dest.getClass(), classK, false);
		try {
			K k = classK.newInstance();
			bc.copy(dest, k, null);
			return k;
		} catch (Exception e) {
			return null;
		}
	}

	public static <T, K> List<K> copyProperties(List<T> dest, Class<T> classt, Class<K> classK) {
		if (dest == null || CollectionUtils.isEmpty(dest))
			return new ArrayList<K>();
		BeanCopier bc = BeanCopier.create(classt, classK, false);
		List<K> list = new ArrayList<K>();
		try {
			for (T t : dest) {
				K k = classK.newInstance();
				bc.copy(t, k, null);
				list.add(k);
			}
		} catch (Exception e) {
			return new ArrayList<K>();
		}
		return list;
	}

}
