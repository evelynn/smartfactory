Ext.define('RPT.view.NavReport', {
	extend: 'Ext.view.View',
	
	store: 'RPT.store.ReportListStore',
	
	listeners: {
		itemclick: function(view, record, item, index, e, opt) {
			SmartFactory.addContentView({
				//xtype: record.get('xtype'),
				xtype: 'rpt.report.report', 
				title: record.get('report_id') + ' - ' + record.get('report_desc'),
				data: record,
				closable: true
			});
		}
	},
	
	autoScroll: true,
	
	cls: 'report-list',
	itemSelector: '.report-list-item',
	overItemCls: 'report-list-item-hover',
	tpl:'<tpl for="."><div class="report-list-item">{report_id} - {report_desc}</div></tpl>'
});
