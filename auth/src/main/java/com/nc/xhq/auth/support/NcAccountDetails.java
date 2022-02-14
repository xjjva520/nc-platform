package com.nc.xhq.auth.support;


import com.nc.auth.core.entity.NcPrincipal;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 账号信息拓展
 *
 * @author sinocare.com
 */
@Getter
public class NcAccountDetails implements UserDetails {
	private static final long serialVersionUID = 510L;
	private final String username;
	private final String password;
	private final Set<GrantedAuthority> authorities;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled;
	private final NcPrincipal principal;


	public NcAccountDetails(String account, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, NcPrincipal principal) {
		this.username = account;
		this.password = password;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
		this.principal = principal;
	}


	/**
	 * 对Authority排序
	 *
	 * @param authorities Authority集合
	 * @return
	 */
	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet(new AuthorityComparator());

		for (GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	/**
	 * Authority比较器
	 */
	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = 510L;

		private AuthorityComparator() {
		}

		@Override
		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			if (g2.getAuthority() == null) {
				return -1;
			} else {
				return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
			}
		}
	}

}
