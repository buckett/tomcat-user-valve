Tomcat User Valve
================

A quick tomcat valve which sets a remote user on a request based on a user's entry
in a form.

Installation
------------

Drop the jar file into the lib folder at the toplevel of the Tomcat distribution.

Configuration
-------------

In your tomcat server configuration file (eg server.xml) add a Valve element to a <Engine>,
<Host> or <Context>. The requestURI attribute specifies the request URI which must match
to require a user parameter on the request.
  
    <!-- Example showing a context which has all /login requests protected. -->
    <Context docBase="quick-test" path="/quick-test" reloadable="true">
      <Valve className="org.bumph.RemoteUserValve" requestURI="/quick-test/login.*" />
    </Context>

Notes
-----

See http://stackoverflow.com/questions/7553967/