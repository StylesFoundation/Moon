package com.moon.module.sub;

import com.moon.module.Module;

public interface SubModuleRegistry<T extends Module> {
   SubModule<T> getSubModule();
}
