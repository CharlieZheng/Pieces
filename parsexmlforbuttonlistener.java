/**
	 * 解析按钮的Click事件响应
	 */
	protected void ParseButtonIdClick() {
		XmlResourceParser xmlParser = rootView.getResources().getXml(
				R.xml.button_id_click);

		int event = XmlPullParser.START_DOCUMENT;

		try {
			event = xmlParser.getEventType();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		String pkgName = (new Throwable()).getStackTrace()[0].getClassName();
		System.out.println(pkgName);

		do {
			switch (event) {
			case XmlPullParser.START_TAG: {
				if (xmlParser.getName().equals("Button")) {
					String id_str = xmlParser.getAttributeValue(0);
					String click = xmlParser.getAttributeValue(1);

					try {
						Class<?> classRId = Class
								.forName("com.abc.keyboard.R$id");
						Field filedId = classRId.getField(id_str);
						int id_int = filedId.getInt(null);
						Button idButton = (Button) rootView
								.findViewById(id_int);

						final Method method = getClass().getDeclaredMethod(
								click, View.class);
						idButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								try {
									method.invoke(KeyboardFragmentBase.this, v);
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							}
						});

					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
				break;
			}

			try {
				event = xmlParser.next();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} while (event != XmlPullParser.END_DOCUMENT);

		xmlParser.close();
	}