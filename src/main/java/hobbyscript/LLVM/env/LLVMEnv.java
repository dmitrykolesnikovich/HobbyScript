package hobbyscript.LLVM.env;/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import hobbyscript.Eval.Env.LocalEnvironment;
import hobbyscript.LLVM.instruction.IRBuilder;
import org.bytedeco.llvm.LLVM.LLVMModuleRef;
import org.bytedeco.llvm.global.LLVM;

public class LLVMEnv extends LocalEnvironment {
    private static IRBuilder irBuilder = IRBuilder.of();
    private static LLVMModuleRef module = LLVM.LLVMModuleCreateWithName("run llvm");

    public <T> T get(String name, Class<T> clazz) {
        return clazz.cast(get(name));
    }

    public IRBuilder getIrBuilder() {
        return irBuilder;
    }

    public boolean isGlobal() {
        return parentEnv == null;
    }

    public LLVMModuleRef getModule() {
        return module;
    }
}
