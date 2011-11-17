Ext.define('MBI.view.FtrfldNtView', {
	extend: 'Ext.grid.Panel',
	
	store : Ext.getStore('test'),
	autoScroll : true,
	
	columns : [ {
		xtype : 'gridcolumn',
		dataIndex : 'field_val_index',
		align : 'center',
		text : 'Field Val Index'
	}, {
		xtype : 'gridcolumn',
		dataIndex : 'sheet_id',
		align : 'center',
		text : 'Sheet Id'
	}, {
		xtype : 'gridcolumn',
		dataIndex : 'func_template_id',
		align : 'center',
		text : 'Func Template Id'
	}, {
		xtype : 'gridcolumn',
		dataIndex : 'func_tmp_field_id',
		align : 'center',
		text : 'Func Tmp Field Id'
	}, {
		xtype : 'gridcolumn',
		dataIndex : 'fac_id',
		align : 'center',
		text : 'Fac Id'
	}]

});