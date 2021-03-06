/*
 * Copyright (c) 2020 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.features.userdirectory

import im.vector.app.core.platform.VectorViewEvents
import im.vector.app.features.discovery.ServerAndPolicies

/**
 * Transient events for invite users to room screen
 */
sealed class UserListViewEvents : VectorViewEvents {
    data class Failure(val throwable: Throwable) : UserListViewEvents()
    data class OnPoliciesRetrieved(val identityServerWithTerms: ServerAndPolicies?) : UserListViewEvents()
    data class OpenShareMatrixToLink(val link: String) : UserListViewEvents()
}
