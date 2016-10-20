/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.svg.support.svg;

import java.util.ArrayList;

/**
 * Used to represent one VectorDrawble's group element.
 * TODO: Add group transformation here.
 */
class VdGroup extends VdElement {
    public VdGroup() {
        mName = this.toString(); // to ensure paths have unique names
    }

    // Children can be either a {@link VdPath} or {@link VdGroup}
    private ArrayList<VdElement> mChildren = new ArrayList<VdElement>();

    public void add(VdElement pathOrGroup) {
        mChildren.add(pathOrGroup);
    }

    public ArrayList<VdElement> getChildren() {
        return mChildren;
    }

    public int size() {
        return mChildren.size();
    }
}