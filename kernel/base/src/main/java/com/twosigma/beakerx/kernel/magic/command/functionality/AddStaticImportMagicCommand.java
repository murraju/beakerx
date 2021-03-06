/*
 *  Copyright 2017 TWO SIGMA OPEN SOURCE, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.twosigma.beakerx.kernel.magic.command.functionality;

import com.twosigma.beakerx.kernel.AddImportStatus;
import com.twosigma.beakerx.kernel.ImportPath;
import com.twosigma.beakerx.kernel.KernelFunctionality;
import com.twosigma.beakerx.kernel.magic.command.MagicCommandExecutionParam;
import com.twosigma.beakerx.kernel.magic.command.MagicCommandFunctionality;
import com.twosigma.beakerx.kernel.magic.command.outcome.MagicCommandOutcomeItem;
import com.twosigma.beakerx.kernel.magic.command.outcome.MagicCommandOutput;

import static com.twosigma.beakerx.kernel.magic.command.functionality.AddImportMagicCommand.IMPORT;
import static com.twosigma.beakerx.kernel.magic.command.outcome.MagicCommandOutcomeItem.Status.ERROR;

public class AddStaticImportMagicCommand implements MagicCommandFunctionality {

  private static final String STATIC = "static";
  public static final String ADD_STATIC_IMPORT = IMPORT + " " + STATIC;
  private KernelFunctionality kernel;

  public AddStaticImportMagicCommand(KernelFunctionality kernel) {
    this.kernel = kernel;
  }

  @Override
  public String getMagicCommandName() {
    return ADD_STATIC_IMPORT;
  }

  @Override
  public boolean matchCommand(String command) {
    String[] commandParts = MagicCommandUtils.splitPath(command);
    return commandParts.length > 2 && commandParts[0].equals(IMPORT) && commandParts[1].equals(STATIC);
  }

  @Override
  public MagicCommandOutcomeItem execute(MagicCommandExecutionParam param) {
    String command = param.getCommand();
    String[] parts = MagicCommandUtils.splitPath(command);
    if (parts.length != 3) {
      return new MagicCommandOutput(MagicCommandOutput.Status.ERROR, WRONG_FORMAT_MSG);
    }
    AddImportStatus status = this.kernel.addImport(new ImportPath(parts[1] + " " + parts[2]));
    if (AddImportStatus.ERROR.equals(status)) {
      return new MagicCommandOutput(ERROR, "Could not import static " + parts[2]);
    }
    return new MagicCommandOutput(MagicCommandOutput.Status.OK);
  }
}
