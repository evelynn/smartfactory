Ext.define('plugin.UserInterface', {
	addNav : function(view, config) {
		var defaults = {
			tabConfig : {
				width : 29,
				height : 22,
				padding : '0 0 0 2px'
			}
		};

		try {
			var navView = Ext.create(view, Ext.merge(defaults, config));
			Ext.getCmp('nav').add(navView);

			SmartFactory.search.register({
				kind : 'nav', 
				key : config.itemId, 
				name : config.title, 
				handler : function(item) {
					Ext.getCmp('nav').setActiveTab(navView);
				}
			});
		} catch (e) {
			console.log(e);
			console.trace();
		}
	},

	addSideMenu : function(view, config) {
		try {
			var sidemenu = Ext.getCmp('sidemenu');
			var menu = Ext.create(view, config);

			sidemenu.insert(0, menu);

			var width = 6; // TODO should be more systemic.

			sidemenu.items.each(function(el) {
				width += el.getWidth();
			});

			sidemenu.setSize(width, sidemenu.getHeight());
		} catch (e) {
			// console.log(e);
		}
	},

	addContentView : function(view) {
		var comp;
		
		if (typeof (view) === 'string') {
			comp = Ext.create(view, {
				closable : true
			});
			Ext.getCmp('content').add(comp);
		} else {
			if(view.itemId) {
				comp = Ext.getCmp('content').getComponent(view.itemId);
			} 
			
			if(!comp) {
				view.closable = true;
				comp = Ext.getCmp('content').add(view);
			}
		}
		
		comp.show();
	},
	
	addActiveContentView : function(view) {		
		var comp;

		if (typeof (view) === 'string') {
			comp = Ext.create(view, {
				closable : true
			});
		} else {
			comp = view;
		}
		
		var tabActive = false;
		var tabpanel = Ext.getCmp('content');
		for(var i in tabpanel.items.items) {
			if(view.title == tabpanel.items.items[i].title)
			{
				tabActive = true;
				comp = tabpanel.items.items[i];
			}
		}
		
		if(tabActive)
		{
			tabpanel.setActiveTab(comp);
		}
		else
		{
			tabpanel.add(comp).show();
		}
	},

	doMenu : function(menu) {
		if (menu.viewModel) {
			Ext.require(menu.viewModel, function() {
				SmartFactory.addContentView(Ext.create(menu.viewModel, {
					title : menu.text,
					tabConfig : {
						tooltip : menu.tooltip
					},
					closable : true
				}));
			});
		} else {
			SmartFactory.status.set({
				text : 'View Not Found!',
				iconCls : 'x-status-error',
				clear : true
			// auto-clear after a set interval
			});
		}
	}
});
