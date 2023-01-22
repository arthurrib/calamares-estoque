import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProdutoFornecedor, NewProdutoFornecedor } from '../produto-fornecedor.model';

export type PartialUpdateProdutoFornecedor = Partial<IProdutoFornecedor> & Pick<IProdutoFornecedor, 'id'>;

export type EntityResponseType = HttpResponse<IProdutoFornecedor>;
export type EntityArrayResponseType = HttpResponse<IProdutoFornecedor[]>;

@Injectable({ providedIn: 'root' })
export class ProdutoFornecedorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/produto-fornecedors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(produtoFornecedor: NewProdutoFornecedor): Observable<EntityResponseType> {
    return this.http.post<IProdutoFornecedor>(this.resourceUrl, produtoFornecedor, { observe: 'response' });
  }

  update(produtoFornecedor: IProdutoFornecedor): Observable<EntityResponseType> {
    return this.http.put<IProdutoFornecedor>(
      `${this.resourceUrl}/${this.getProdutoFornecedorIdentifier(produtoFornecedor)}`,
      produtoFornecedor,
      { observe: 'response' }
    );
  }

  partialUpdate(produtoFornecedor: PartialUpdateProdutoFornecedor): Observable<EntityResponseType> {
    return this.http.patch<IProdutoFornecedor>(
      `${this.resourceUrl}/${this.getProdutoFornecedorIdentifier(produtoFornecedor)}`,
      produtoFornecedor,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProdutoFornecedor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProdutoFornecedor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProdutoFornecedorIdentifier(produtoFornecedor: Pick<IProdutoFornecedor, 'id'>): number {
    return produtoFornecedor.id;
  }

  compareProdutoFornecedor(o1: Pick<IProdutoFornecedor, 'id'> | null, o2: Pick<IProdutoFornecedor, 'id'> | null): boolean {
    return o1 && o2 ? this.getProdutoFornecedorIdentifier(o1) === this.getProdutoFornecedorIdentifier(o2) : o1 === o2;
  }

  addProdutoFornecedorToCollectionIfMissing<Type extends Pick<IProdutoFornecedor, 'id'>>(
    produtoFornecedorCollection: Type[],
    ...produtoFornecedorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const produtoFornecedors: Type[] = produtoFornecedorsToCheck.filter(isPresent);
    if (produtoFornecedors.length > 0) {
      const produtoFornecedorCollectionIdentifiers = produtoFornecedorCollection.map(
        produtoFornecedorItem => this.getProdutoFornecedorIdentifier(produtoFornecedorItem)!
      );
      const produtoFornecedorsToAdd = produtoFornecedors.filter(produtoFornecedorItem => {
        const produtoFornecedorIdentifier = this.getProdutoFornecedorIdentifier(produtoFornecedorItem);
        if (produtoFornecedorCollectionIdentifiers.includes(produtoFornecedorIdentifier)) {
          return false;
        }
        produtoFornecedorCollectionIdentifiers.push(produtoFornecedorIdentifier);
        return true;
      });
      return [...produtoFornecedorsToAdd, ...produtoFornecedorCollection];
    }
    return produtoFornecedorCollection;
  }
}
