<?xml version="1.0" encoding="ISO-8859-1"?>
<Make:Makefile xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:Make="http://www.eclipse.org/atl/atlTransformations/Make" name="makefile" ID="0">
  <comment text="Test d'un fichier makefile" ID="1"/>
  <elements xsi:type="Make:Macro" name="CC" ID="2" value="gcc"/>
  <elements xsi:type="Make:Macro" name="CFLAGS" ID="3" value="-wall -ansi"/>
  <elements xsi:type="Make:Macro" name="LDFLAGS" ID="4" value="-wall -ansi"/>
  <elements xsi:type="Make:Rule" name="hello" ID="5">
    <dependencies xsi:type="Make:RuleDep" ID="6" ruledep="//@elements.5"/>
    <dependencies xsi:type="Make:RuleDep" ID="7" ruledep="//@elements.4"/>
    <shellLines command="$(CC) -o hello hello.o main.o $(LDFLAGS)" ID="8"/>
    <shellLines command="skip" display="true" ID="9"/>
  </elements>
  <elements xsi:type="Make:Rule" name="hello.o" ID="10">
    <dependencies xsi:type="Make:FileDep" ID="11" name="hello.c"/>
    <shellLines command="$(CC) -o hello.o -c hello.c $(CFLAGS)" ID="12"/>
  </elements>
  <elements xsi:type="Make:Rule" name="main.o" ID="13">
    <shellLines command="$(CC) -o main.o -c main.c $(CFLAGS)" display="true" ID="16"/>
  </elements>
  <elements xsi:type="Make:Rule" name="clean" ID="17">
    <shellLines command="rm -rf *.o" display="true" ID="18"/>
  </elements>
  <elements xsi:type="Make:Rule" name="mrproper" ID="19">
    <dependencies xsi:type="Make:RuleDep" ID="20" ruledep="//@elements.6"/>
    <shellLines command="rm -rf $(EXEC)" ID="21"/>
  </elements>
</Make:Makefile>
