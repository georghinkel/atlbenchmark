<?xml version="1.0" encoding="utf-8"?>
<Make:Makefile xmi:version="2.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" name="makefile name" ID="0" xmlns:xmi="http://www.omg.org/XMI" xmlns:Make="http://www.eclipse.org/atl/atlTransformations/Make">
  <elements xsi:type="Make:Macro" value="macro value1" name="macro name1" ID="1" />
  <elements xsi:type="Make:Rule" name="RuleName2" ID="2">
    <dependencies xsi:type="Make:FileDep" name="fileName3" ID="3" />
    <dependencies xsi:type="Make:RuleDep" ruledep="#//@elements.1/" ID="8" />
    <dependencies xsi:type="Make:RuleDep" ruledep="#//@elements.1/" ID="9" />
    <shellLines command="command shell line 4" display="True" ID="4" />
  </elements>
  <elements xsi:type="Make:Rule" name="RuleName5" ID="5">
    <dependencies xsi:type="Make:FileDep" name="fileName6" ID="6" />
    <shellLines command="command shell line 7" display="True" ID="7" />
  </elements>
</Make:Makefile>