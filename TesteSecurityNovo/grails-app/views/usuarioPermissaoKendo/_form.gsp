<div id="example">

	<div class="demo-section k-header">
		Permissões
		<div id="treeview"></div>
		<input id="result" name="result" type="hidden" />
	</div>

	<script>
		$("#treeview").kendoTreeView({
			checkboxes : {
				checkChildren : true
			},

			check : onCheck,

			dataSource : ${raw(retorno)}

		});

		// function that gathers IDs of checked nodes
		function checkedNodeIds(nodes, checkedNodes) {
			for (var i = 0; i < nodes.length; i++) {
				if (nodes[i].checked) {
					checkedNodes.push(nodes[i].id);
				}

				if (nodes[i].hasChildren) {
					checkedNodeIds(nodes[i].children.view(), checkedNodes);
				}
			}
		}

		// show checked node IDs on datasource change
		function onCheck() {
			var checkedNodes = [], treeView = $("#treeview").data(
					"kendoTreeView"), message;

			checkedNodeIds(treeView.dataSource.view(), checkedNodes);

			if (checkedNodes.length > 0) {
				message = checkedNodes.join(",");
			} else {
				message = "";
			}

			$("#result").val(message);
		}

		onCheck();
	</script>

	<style>
#treeview .k-sprite {
	background-image: url("../assets/kendo/coloricons-sprite.png");
}

.rootfolder {
	background-position: 0 0;
}

.folder {
	background-position: 0 -16px;
}

.pdf {
	background-position: 0 -32px;
}

.html {
	background-position: 0 -48px;
}

.image {
	background-position: 0 -64px;
}
</style>
</div>
