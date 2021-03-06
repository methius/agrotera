# Change Log

## Version 0.3.0 - 2013-11-24
 - IOOB exception during agrotera:matrix if no systems/managers were processed.
 - Java 1.6 support.
 - `@ArtemisInjected` annotation for injecting artemis systems, managers and mappers into arbitrary classes.
 - Maven goal `annotation-cleaner` removes agrotera annotations from compiled classes.
 - **Fix**: requiresOne mappers weren't injected.

## Version 0.2.1 - 2013-08-08
 - Multithreaded class weaving.
 - Component Dependency Matrix (agrotera:matrix)
   - First entry system/manager was omitted.
   - Added icons for systems and managers.
   - Floating header size fixed.

## Version 0.2.0 - 2013-06-23
 - `@ArtemisManager`, like `@ArtemisSystem` but for Managers.
 - `agrotera:matrix` renders _Component Dependency Matrix_ - showing
   mappings between systems, managers and components.
 - `@ArtemisSystem` handles `Aspect#one`.
 - Less bloat in pom when working with Eclipse + Maven.

## Version 0.1.1 - 2013-05-05
 - Initial release: support for `@ArtemisSystem` and `@Profiler`.
 
