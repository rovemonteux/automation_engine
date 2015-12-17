**Monteux Automation Engine**
====================

**Monteux Automation Engine** is a GPLed framework for Java SE 6 or newer that implements a Lego(tm)-like modular, plug and play Automation Framework with a localized built-in shell and script interpreter.

The automation is based on **tasks lists** and **object stacks**, interacting with the environment via **HIDs**. 

**Tasks** are classes described in custom **vocabularies**, with three entry points: **run**, **stack** and **print**.

**run** runs a full cycle of the task, typically running, pushing the result into the stack, and pritning the result to the **HID** stream.

**stack** runs the task and pushes the result to the **stack**.

**print** runs the task and prints the result to the **HID** output stream.

**Object Stack** is an in-memory database of objects acting as a **stack**, with add, search, list, retrieve and clear functionalities. Objects in the **stack** are immediately available for all running **tasks**.

**HID**s are devices for interacting with the automation engine, such as **Console** for a Readline-enabled, interactive shell, and **Script** for a headless engine.

Examples are included in the distribution, in the folders,

**test/**
scripts for starting the engine.

**scripts/**
scripts to be included and trun at the engine's startup.

**vocabularies/**
vocabularies binding tasks to commands to be executed via scripts or interactively in the HIDs.

Build
=====

For building the **Monteux Automation Engine**, you will need Maven. In the project folder, type

```
mvn package
```
  
Usage
=====

```
java -jar target/Monteux_Automation_Engine-0.0.1-SNAPSHOT-jar-with-dependencies.jar -hid Console -vocabulary vocabularies/test_vocabulary.xml -script scripts/test_script_custom_vocabulary.txt -language en
```

**-hid**
Selects the desired **HID**, i.e. 'Console' for the interactive shell, 'Script' for the headless script interpreter.

The below snippet is an example **Console HID** session started as root with the **-hid Console**,

```
2015-12-15 13:50:44,528 INFO  [main] hid.Console (Console.java:59) - Console HID started
2015-12-15 13:50:44,529 INFO  [main] hid.Console (Console.java:64) - Setting up the Console
2015-12-15 13:50:44,530 INFO  [main] hid.Console (Console.java:66) - Set the Console as the system's console, starting up
Console auto completion is enabled, type the first letter of the command, and then the <tab> key. A <tab> on its own lists all available commands. Command history is available by pressing the up and down keys.
root@mae> list files 
2015-12-15 13:50:49,516 DEBUG [main] tasks.ListFiles (ListFiles.java:72) - Listing folder .
run_script_test.sh
run_console_test_custom_vocabulary.sh
run_script_test_custom_vocabulary.sh
run_console_test.sh
root@mae> view file run_console_test.sh
java -jar ../target/Monteux_Automation_Engine-0.0.1-SNAPSHOT-jar-with-dependencies.jar -hid Console -vocabulary builtin -script ../scripts/test_script.txt
root@mae> view file ../scripts/test_script.txt
stack free memory
print free memory
empty stack
root@mae> run script ../scripts/test_script.txt
2015-12-15 13:58:37,841 INFO  [main] hid.Script (Script.java:52) - Script HID started
2015-12-15 13:58:37,842 INFO  [main] hid.Script (Script.java:65) - Executing script /usr/development/rove/automation_engine/test/../scripts/test_script.txt
2015-12-15 13:58:37,843 INFO  [main] io.LoggingOutputStream (LoggingOutputStream.java:125) - Free memory: 106609496

2015-12-15 13:58:37,844 INFO  [main] io.LoggingOutputStream (LoggingOutputStream.java:125) - Emptied the object stack.

2015-12-15 13:58:37,844 INFO  [main] hid.Script (Script.java:69) - Executed script /usr/development/rove/automation_engine/test/../scripts/test_script.txt
root@mae> list files 
2015-12-16 08:39:54,127 DEBUG [main] tasks.ListFiles (ListFiles.java:72) - Listing folder .
run_script_test.sh
run_console_test_custom_vocabulary.sh
run_script_test_custom_vocabulary.sh
run_console_test.sh
root@mae> copy file run_console_test.sh run_console_test.sh.copy
2015-12-16 08:40:03,412 DEBUG [main] tasks.CopyFile (CopyFile.java:68) - Copied run_console_test.sh to run_console_test.sh.copy
File run_console_test.sh has been successfully copied to run_console_test.sh.copy.
root@mae> list files
2015-12-16 08:40:25,621 DEBUG [main] tasks.ListFiles (ListFiles.java:72) - Listing folder .
run_script_test.sh
run_console_test_custom_vocabulary.sh
run_console_test.sh.copy
run_script_test_custom_vocabulary.sh
run_console_test.sh
root@mae> delete file run_console_test.sh.copy
2015-12-16 08:41:32,153 DEBUG [main] tasks.DeleteFile (DeleteFile.java:60) - Deleted file run_console_test.sh.copy.
root@mae> 
```

**-vocabulary**
Custom vocabulary binding **Tasks** to commands, use 'builtin' or don't send the flag for no custom vocabulary.

**-script**
Custom script containing commands to be run at the engine's initialization.

**-language**
Language code to use for commands and messages, i.e. '-language en' for English (default), and '-language pt' for Portuguese. 

Extensions
==========

To extend the **Monteux Automation Engine**, simply write your own **Task**, extending net.rovemonteux.automation.engine.tasks.TaskFactory, and bind it as a **Task** using a custom vocabulary, for example,

```
package my.package.name;

public class MyExtension extends TaskFactory {
```

bind it as a **Task** with a custom **vocabulary** term in the **vocabulary** file,

```
<term>
<language>en</language>
<value>my extension</value>
<task mode="run">MyExtension</task>
<threaded>false</threaded>
<description>Runs my custom extension</description>
<package>my.package.name</package>
</term>
```

Then run the new extension in the **Monteux Automation Engine** via the command "my extension" - the "run()" method of the class "MyExtension" will then be given access to the **Object Stack** and run in non-threaded mode.

To run in threaded mode, in its own thread, change the threaded configuration tag from

```
<threaded>false</threaded>
```

to

```
<threaded>true</threaded>
```

To run the "stack()" or "print()" methods, simply change the **mode** attribute in **task** accordingly.

You can also chain tasks together to a single command, as in for example,

```
<term>
<language>en</language>
<value>my extension</value>
<task mode="print">ShowStack</task>
<task mode="stack">MyExtension</task>
<task mode="print">MyExtension</task>
<task mode="run">MyExtension</task>
<task mode="print">ShowStack</task>
<threaded>false</threaded>
<description>Runs my custom extension</description>
<package>my.package.name</package>
</term>
```

An example **extension** is the Empty Stack one, which clears the **object stack** via the "empty stack" command in the **Monteux Automation Engine**,

**Extension source code:** https://github.com/rovemonteux/automation_engine/blob/master/src/main/java/net/rovemonteux/automation/engine/tasks/EmptyStack.java

**Vocabulary:** https://github.com/rovemonteux/automation_engine/blob/master/vocabularies/test_vocabulary.xml

Author
======

* [Rove Monteux] - <https://rmonteux.wordpress.com/>, <https://www.linkedin.com/in/rovemonteux>

License
=======

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

Project Wiki
============

* [https://github.com/rovemonteux/automation_engine/wiki](https://github.com/rovemonteux/automation_engine/wiki)
