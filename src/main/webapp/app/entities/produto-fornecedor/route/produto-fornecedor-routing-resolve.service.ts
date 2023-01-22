import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProdutoFornecedor } from '../produto-fornecedor.model';
import { ProdutoFornecedorService } from '../service/produto-fornecedor.service';

@Injectable({ providedIn: 'root' })
export class ProdutoFornecedorRoutingResolveService implements Resolve<IProdutoFornecedor | null> {
  constructor(protected service: ProdutoFornecedorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProdutoFornecedor | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((produtoFornecedor: HttpResponse<IProdutoFornecedor>) => {
          if (produtoFornecedor.body) {
            return of(produtoFornecedor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
